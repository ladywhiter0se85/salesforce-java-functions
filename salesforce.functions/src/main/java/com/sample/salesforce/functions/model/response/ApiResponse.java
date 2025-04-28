package com.sample.salesforce.functions.model.response;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class ApiResponse<T> {
    public HttpStatusCode statusCode;
    public String message;
    public T data;

    public ApiResponse(T data) {
        statusCode = HttpStatusCode.valueOf(200);
        this.data = data;
    }

    public ApiResponse(HttpStatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
