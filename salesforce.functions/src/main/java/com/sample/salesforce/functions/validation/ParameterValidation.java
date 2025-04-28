package com.sample.salesforce.functions.validation;

import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.model.response.ApiResponse;
import org.springframework.http.HttpStatusCode;

public class ParameterValidation {
    public static String validateStringParam(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new ApiResponseException(new ApiResponse<String>(HttpStatusCode.valueOf(400), String.format("The parameter '%s' is required.", paramName)));
        }
        return value;
    }

    public static boolean validateBooleanParam(String value, String paramName) {
        if (value == null) {
            throw new ApiResponseException(new ApiResponse<>(HttpStatusCode.valueOf(400), String.format("The parameter '%s' is required.", paramName)));
        }

        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new ApiResponseException(new ApiResponse<>(HttpStatusCode.valueOf(400), String.format("The parameter '%s' must be a boolean", paramName)));
        }

        return Boolean.parseBoolean(value);
    }
}
