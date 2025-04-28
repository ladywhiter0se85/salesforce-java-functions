package com.sample.salesforce.functions.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.model.response.ApiResponse;
import org.springframework.http.HttpStatusCode;

public class BodyValidation {
    public static String validate(String body) {
        if (body.isBlank()) {
            throw new ApiResponseException(new ApiResponse<String>(HttpStatusCode.valueOf(400), "The request body is required."));
        }
        return body;
    }

    public static String validateAndCheckIds(String body) {
        if (body.isBlank()) {
            throw new ApiResponseException(new ApiResponse<String>(HttpStatusCode.valueOf(400), "The request body is required."));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // Parse the body into a JsonNode (array of objects)
        JsonNode array;
        try {
            array = objectMapper.readTree(body);
            // Iterate over each element in the array
            for (JsonNode item : array) {
                // Check if either 'id' or 'Id' field is missing or empty
                JsonNode idNode = item.get("id");
                if (idNode == null || idNode.asText().trim().isEmpty()) {
                    // If 'id' is not found, check for 'Id'
                    idNode = item.get("Id");
                    if (idNode == null || idNode.asText().trim().isEmpty()) {
                        throw new ApiResponseException(new ApiResponse<>(HttpStatusCode.valueOf(400), "Each object in a PATCH request must include an 'Id' field."));
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
}
