package com.sample.salesforce.functions.utility.mocked.responses;

import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatusType;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

public class MockHttpResponseMessage implements HttpResponseMessage {
    private final HttpStatusCode statusCode;
    private Object body;
    private final Map<String, String> headers = new HashMap<>();

    public MockHttpResponseMessage(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public HttpStatusType getStatus() {
        return HttpStatusType.custom(statusCode.value());
    }

    @Override
    public String getHeader(String s) {
        return headers.get(s);
    }

    @Override
    public Object getBody() {
        return body;
    }

    public static class Builder implements HttpResponseMessage.Builder {
        private final HttpStatusCode statusCode;
        private Object body;
        private final Map<String, String> headers = new HashMap<>();

        public Builder(HttpStatusCode statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public HttpResponseMessage.Builder status(HttpStatusType httpStatusType) {
            return this;
        }

        @Override
        public HttpResponseMessage.Builder header(String s, String s1) {
            headers.put(s, s1);  // Store headers in the map
            return this;
        }

        @Override
        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        @Override
        public HttpResponseMessage build() {
            MockHttpResponseMessage response = new MockHttpResponseMessage(statusCode);
            response.body = body;
            response.headers.putAll(headers);
            return response;
        }
    }
}
