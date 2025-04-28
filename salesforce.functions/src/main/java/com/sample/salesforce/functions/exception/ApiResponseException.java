package com.sample.salesforce.functions.exception;

import com.sample.salesforce.functions.model.response.ApiResponse;
import lombok.Getter;

@Getter
public class ApiResponseException extends RuntimeException {
    public ApiResponse<?> apiResponse;

    public ApiResponseException(ApiResponse<?> apiResponse) {
        super(apiResponse.getMessage());
        this.apiResponse = apiResponse;
    }
}
