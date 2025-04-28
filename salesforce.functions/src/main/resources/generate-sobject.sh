#!/bin/bash
set -e # Exit immediately if a command fails

# Script Overview:
# This script generates Java classes for Salesforce SObjects by querying Salesforce's metadata API.
# It authenticates using the password grant type and retrieves metadata for a specified SObject.
# The generated classes include properties based on the fields of the SObject and a shared Attributes class.

# NOTE: Any subclasses will need to be added with this script and then manually updated to main class (i.e Account -> Address for BillingAddress)

# Function to show help
show_help() {
	echo "Usage: $0 --username USERNAME --password PASSWORD --client-id CLIENT_ID --client-secret CLIENT_SECRET --sobject SOBJECT"
	echo ""
	echo "  --username        Salesforce username"
	echo "  --password        Salesforce password + security token"
	echo "  --client-id       Salesforce Connected App client ID"
	echo "  --client-secret   Salesforce Connected App client secret"
	echo "  --sobject         Salesforce object to describe (e.g., Account)"
	exit 1
}

# Log function
log() {
	echo "$(date +'%Y-%m-%d %H:%M:%S') - $1"
}

# Function to create class file
create_class_file() {
	local class_file=$1
	local class_name=$2
	cat <<EOF >"$class_file" # Use the full file path here
package $JAVA_PACKAGE;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class $class_name {
EOF
	log "$class_name class created at $class_file"
}

# Function to get the java type based on the Salesforce field type
get_java_type() {
	case "$1" in
	"picklist" | "textarea" | "string" | "email" | "phone" | "url" | "id") echo "String" ;;
	"boolean") echo "boolean" ;;
	"double" | "currency") echo "double" ;;
	"int") echo "int" ;;
  "date")
    add_import_if_missing "java.time.LocalDate" "$SOBJECT_FILE"
    echo "LocalDate"
    ;;
  "datetime")
    add_import_if_missing "java.time.OffsetDateTime" "$SOBJECT_FILE"
    echo "OffsetDateTime"
    ;;
  *)

  # Check of Type matches current SObject and set to match
    SOBJECT_LOWER=$(to_camel_case "$SOBJECT")

    if [[ "$1" == "$SOBJECT_LOWER" ]]; then
        echo "$SOBJECT"
    else
        echo "Object"
    fi
		;;
	esac
}

# Function to lowercase the first letter of a name
to_camel_case() {
  local str="$1"
  echo "$(tr '[:upper:]' '[:lower:]' <<< "${str:0:1}")${str:1}"
}

# Function to handle reference fields and add missing references
process_references() {
	local field=$1

	# Extract the referenceTo part (assuming it's the 3rd part in the description)
	REF_OBJECTS=$(echo "$field" | cut -d: -f3-)
	REF_OBJECTS=$(echo "$REF_OBJECTS" | tr -d '[]"')

	IFS=',' read -r -a REF_OBJECTS_ARRAY <<<"$REF_OBJECTS"

	for REF_OBJECT in "${REF_OBJECTS_ARRAY[@]}"; do
		REF_OBJECT=$(echo "$REF_OBJECT" | tr -d '"') # Clean up extra characters

		# Extract the name (field name) from the field description
		INITIAL_NAME=$(echo "$FIELD" | cut -d: -f1)
    NAME=$(to_camel_case "$INITIAL_NAME")

		# Check if the reference is the same as the SObject
		if [[ "$REF_OBJECT" == "$SOBJECT" ]]; then
			# If it's the same, use the reference object as it is
			echo "    public $REF_OBJECT $NAME;" >>"$SOBJECT_FILE"
		else
			# If it's not the same, use Object as the reference type
			echo "    public Object $NAME;" >>"$SOBJECT_FILE"

			# Log the missing reference
			if ! grep -q "$REF_OBJECT" "$MISSING_REFS_FILE"; then
				# Only log the reference if it's not already listed
				echo "$REF_OBJECT" >>"$MISSING_REFS_FILE"
			fi
		fi
	done
}

# Function to remove a reference from the missing references file after it has been added
remove_reference() {
	local ref=$1
	# Remove the reference from the missing references file
	sed -i "/$ref/d" "$MISSING_REFS_FILE"
}

# Function to display missing references if any
display_missing_references() {
	# Check if there are any missing references
	if [ -s "$MISSING_REFS_FILE" ]; then
		echo "Missing References:"
		# List unique references only (use sort -u to remove duplicates)
		sort -u "$MISSING_REFS_FILE"
	else
		echo "No missing references to be added."
	fi
}

# Add Imports to the top of the Java file below package
add_import_if_missing() {
    local import="$1"
    local file="$2"

    # Check if the import already exists
    if ! grep -q "import ${import};" "$file"; then
        # Create a temporary file with blank line + import after package
        awk -v imp="import ${import};" '
        /^package / {
            print;
            print "";
            print imp;
            next
        }
        { print }
        ' "$file" > "${file}.tmp" && mv "${file}.tmp" "$file"
    fi
}

# Shared Variables
LOGIN_URL="https://login.salesforce.com"
API_VERSION="58.0"
PROJECT_DIR=$(pwd)
PACKAGE=$(basename "$PROJECT_DIR")

## Replace dashes with dots to match Java package naming
PACKAGE=${PACKAGE//-/.}

# Set package per Java convention to lowercase
PACKAGE=$(echo "$PACKAGE" | tr '[:upper:]' '[:lower:]')
JAVA_PACKAGE="com.sample.${PACKAGE}.model.salesforce"

echo "Using Java package: $JAVA_PACKAGE"

# File to log missing references
MISSING_REFS_FILE="src/main/resources/missing_references.txt"

# Ensure the directory exists
mkdir -p "$(dirname "$MISSING_REFS_FILE")"

# Create the file if it doesn't exist
if [ ! -f "$MISSING_REFS_FILE" ]; then
    touch "$MISSING_REFS_FILE"
fi

# Parse arguments and check required parameters
while [[ $# -gt 0 ]]; do
	case "$1" in
	--username)
		USERNAME="$2"
		shift 2
		;;
	--password)
		PASSWORD="$2"
		shift 2
		;;
	--client-id)
		CLIENT_ID="$2"
		shift 2
		;;
	--client-secret)
		CLIENT_SECRET="$2"
		shift 2
		;;
	--sobject)
		SOBJECT="$2"
		shift 2
		;;
	--login-url)
		LOGIN_URL="$2"
		shift 2
		;;
	-h | --help) show_help ;;
	*)
		echo "Unknown argument: $1"
		show_help
		;;
	esac
done

# Check required parameters
if [[ -z "$USERNAME" || -z "$PASSWORD" || -z "$CLIENT_ID" || -z "$CLIENT_SECRET" || -z "$SOBJECT" ]]; then
	echo "❌ Missing required parameters."
	show_help
fi

log "Starting script for SObject: $SOBJECT"

# Request access token
log "🔐 Requesting access token..."
RESPONSE=$(curl -k -X POST "${LOGIN_URL}/services/oauth2/token" -d "grant_type=password" -d "client_id=${CLIENT_ID}" -d "client_secret=${CLIENT_SECRET}" -d "username=${USERNAME}" -d "password=${PASSWORD}")
ACCESS_TOKEN=$(echo "$RESPONSE" | grep -o '"access_token":"[^"]*' | sed 's/"access_token":"//')
INSTANCE_URL=$(echo "$RESPONSE" | grep -o '"instance_url":"[^"]*' | sed 's/"instance_url":"//')

# Check if we got the token
if [[ -z "$ACCESS_TOKEN" || "$ACCESS_TOKEN" == "null" ]]; then
	log "❌ Authentication failed."
	exit 1
fi

log "✅ Authenticated. Access token acquired."

#Java directory
JAVA_DIR="src/main/java/com/sample/salesforce/functions"

# Creating Attributes class if not exists
ATTRIB_FILE="$JAVA_DIR/model/salesforce/Attributes.java"
if [ -f "$ATTRIB_FILE" ]; then
	# If it exists, show a green check mark
	echo "✅ Attributes class already exists. No need to create it again."
else
	# If it doesn't exist, create the Attributes class
	create_class_file "$ATTRIB_FILE" "Attributes"
	echo "    @JsonProperty(\"Type\")" >>"$ATTRIB_FILE"
	echo "    private String type;" >>"$ATTRIB_FILE"
	echo "    @JsonProperty(\"Url\")" >>"$ATTRIB_FILE"
	echo "    private String url;" >>"$ATTRIB_FILE"
  echo -e "\n    public Attributes() {" >>"$ATTRIB_FILE"
  echo "    }" >>"$ATTRIB_FILE"
  echo -e "\n    public Attributes(String type) {" >>"$ATTRIB_FILE"
  echo "        this.type = type;" >>"$ATTRIB_FILE"
  echo "    }" >>"$ATTRIB_FILE"
	echo "}" >>"$ATTRIB_FILE"
	echo "✅ Attributes class created."
fi

# Creating SObject class
SOBJECT_FILE="$JAVA_DIR/model/salesforce/${SOBJECT}.java"
echo "Generating file at: $SOBJECT_FILE"
create_class_file "$SOBJECT_FILE" "$SOBJECT"

# Add the Attributes object to the SObject class
echo "    private Attributes attributes = new Attributes(\"$SOBJECT\");" >>"$SOBJECT_FILE"

# Fetching fields from Salesforce
log "🔄 Fetching fields for $SOBJECT..."
FIELDS_JSON=$(curl -k -X GET "${INSTANCE_URL}/services/data/v${API_VERSION}/sobjects/${SOBJECT}/describe" \
	-H "Authorization: Bearer ${ACCESS_TOKEN}" \
	-H "Content-Type: application/json")
FIELDS=$(echo "$FIELDS_JSON" | jq -r '.fields[] | "\(.name):\(.type):\(.referenceTo // "")"')
echo "✅ Successful fetching of $SOBJECT fields."

# Processing fields
for FIELD in $FIELDS; do
	INITIAL_NAME=$(echo "$FIELD" | cut -d: -f1)
	NAME=$(to_camel_case "$INITIAL_NAME")
	TYPE=$(echo "$FIELD" | cut -d: -f2)
	REF_OBJECTS=$(echo "$FIELD" | cut -d: -f3-)

	# Determine the Java property type based on the field type
	if [[ "$TYPE" == "reference" ]]; then
		process_references "$FIELD"
	else
		# Get the Java type by calling the function
		JAVA_TYPE=$(get_java_type "$TYPE")

    # Using Json Alias for Json property names to handle variations like (city, City)
		echo "    @JsonAlias({\"$NAME\", \"$INITIAL_NAME\"})" >>"$SOBJECT_FILE"
		echo "    private $JAVA_TYPE $NAME;" >>"$SOBJECT_FILE"
	fi

done

# Close the class and namespace (not yet adding fields)
echo "}" >>"$SOBJECT_FILE"

echo "✅ $SOBJECT class created."

# After SObject Processed remove from Missing References txt file if exists
remove_reference $SOBJECT
# Then display any additional references that could be manually added and are currently set as Object
display_missing_references
