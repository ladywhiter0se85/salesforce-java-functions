package com.sample.salesforce.functions.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.sample.salesforce.functions.model.response.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@Service
public final class ResponseUtility {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

    public static String readFile(String filePath) throws IOException {
        // Attempt to read from classpath
        try (InputStream is = ResponseUtility.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is != null) {
                try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
                    return scanner.useDelimiter("\\A").next();
                }
            }
        }

        // If not found in classpath, attempt to read from current working directory
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.readString(path, StandardCharsets.UTF_8);
        }

        // If not found in current directory, attempt to read from user.dir
        String currentDir = System.getProperty("user.dir");
        Path localPath = Paths.get(currentDir, filePath);
        if (Files.exists(localPath)) {
            return Files.readString(localPath, StandardCharsets.UTF_8);
        }

        // If the file is not found in any location, throw an exception
        throw new IOException("Resource not found: " + filePath);
    }

    public static HttpResponseMessage buildGetResponse(HttpRequestMessage<Void> request, ApiResponse<?> apiResponse) {
        return buildResponse(request, apiResponse);
    }

    public static HttpResponseMessage buildOperationResponse(HttpRequestMessage<String> request, ApiResponse<?> apiResponse) {
        return buildResponse(request, apiResponse);
    }

    private static HttpResponseMessage buildResponse(HttpRequestMessage<?> request, ApiResponse<?> apiResponse) {
        // Assume the initial status comes from the apiResponse
        HttpStatus status = HttpStatus.valueOf(apiResponse.getStatusCode().value());
        String resp;
        if (apiResponse.getStatusCode() == HttpStatusCode.valueOf(200)) {
            // If status is OK (200), serialize the ApiResponse
            ApiResponse<String> temp = serializeApiResponse(apiResponse);
            resp = temp.getData();
            // If serialization changes status, update it
            status = HttpStatus.valueOf(temp.getStatusCode().value());
        } else {
            // Otherwise, just use the error message
            resp = apiResponse.getMessage();
        }
        return request.createResponseBuilder(status)
                .body(resp)
                .header("Content-Type", "application/json")
                .build();
    }

    private static ApiResponse<String> serializeApiResponse(ApiResponse<?> apiResponse) {
        try {
            if (apiResponse.getStatusCode() == HttpStatusCode.valueOf(200)) {
                String data = objectMapper.writeValueAsString(apiResponse.getData());
                return new ApiResponse<>(data);
            } else {
                return new ApiResponse<>(apiResponse.getStatusCode(), apiResponse.getMessage());
            }
        } catch (JsonProcessingException e) {
            return new ApiResponse<>(HttpStatusCode.valueOf(500), String.format("Failed to serialize API response: %s", e.getMessage()));
        }
    }
}
