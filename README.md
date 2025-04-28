# salesforce-java-functions

## Overview

`salesforce-java-functions` demonstrates how to integrate Salesforce with Azure Function Apps using Java, Maven, and Spring. It generates Java class files based on Salesforce SObject metadata and provides a serverless solution for interacting with Salesforce data through Azure Functions. The project includes automated tests to validate function behavior and functionality.

## Technology Stack

- **Azure Function Apps**: Serverless execution of Java functions
- **Java 17**
- **Spring Framework**: Dependency injection and configuration
- **Maven**: Build and dependency management
- **Salesforce**: CRM platform integration
- **Postman**: API testing
- **OpenAPI**: API documentation

## Project Structure

The project is organized into the following main areas:

- **docs/**  
  Documentation assets, including the Postman collection, and OpenAPI specifications.

- **src/main/java/**  
  Main application code, structured into packages:
    - `handler`: Azure Function HTTP endpoints
    - `function`: Request processing and orchestration
    - `service`: Business logic and Salesforce interactions
    - `model`: Salesforce SObject Java models (generated)
    - `utility`, `validation`, `config`, `exception`: Supporting utilities and configurations

- **src/main/resources/**  
  Application configuration (`application-dev.properties`, `application.properties`), logging setup, model generation scripts, and documentation.

- **src/test/java/**  
  Test classes covering functions, handlers, services, utilities, and configuration.

- **src/test/resources/**  
  Mocked Salesforce data, test configuration files, and logging setup.

### Example folder structure

src └── main ├── java │ ├── function │ ├── handler │ ├── model │ └── service └── resources ├── application-dev.properties ├── generate-sobject.sh └── logback.xml

## Functionality Flow

- Incoming HTTP requests are received by Azure Function classes (`handler` package).
- Handlers delegate the handling of requests to `function` classes.
- Functions invoke `service` classes to perform Salesforce operations or business logic.
- `model` classes represent Salesforce SObjects used for request and response serialization.
- Utilities assist with encryption, response formatting, and common tasks.
- Validation ensures incoming requests meet expected schemas.
- Custom exceptions standardize error handling.

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/ladywhiter0se85/salesforce-java-functions.git
    cd salesforce-java-functions
    ```

2. Configure your local environment (local.settings.json) or Azure environment variables.

3. Generate SObject models:

    ```bash
    ./src/main/resources/generate-sobject.sh
    ```

4. Build and run locally:

    ```bash
   mvn clean package
   mvn azure-functions:run
   ```

5. Use the Postman collection in the docs/ folder to test the API endpoints locally or add other environments to test.

## SObject Generation

The `generate-sobject.sh` script is used to generate Java model classes based on Salesforce SObject metadata.

- Generated classes are placed in the `model/salesforce` package under `src/main/java/`.
- If any references cannot be generated automatically, they are logged in `missing_references.txt`.
- For detailed instructions and troubleshooting, refer to `SObjectGenerator.md`.

## Testing

This project includes unit and integration tests to validate application functionality.

- Unit tests verify individual components like functions, handlers, services, and utilities using mocked data.
- Integration tests simulate full request/response flows against live or mocked Salesforce data.