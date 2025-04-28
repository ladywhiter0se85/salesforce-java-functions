package com.sample.salesforce.functions.utility.mocked.responses;

import com.microsoft.azure.functions.*;
import org.springframework.http.HttpStatusCode;

import java.net.URI;
import java.util.Map;

public class MockHttpRequestMessage<T> implements HttpRequestMessage<T> {
    private final Map<String, String> headers;
    private final Map<String, String> queryParameters;
    private final T body;

    public MockHttpRequestMessage(Map<String, String> headers, Map<String, String> queryParameters, T body) {
        this.headers = headers;
        this.queryParameters = queryParameters;
        this.body = body;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    @Override
    public T getBody() {
        return body;
    }

    @Override
    public HttpResponseMessage.Builder createResponseBuilder(HttpStatus httpStatus) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(httpStatus.value());
        return new MockHttpResponseMessage.Builder(statusCode);
    }

    @Override
    public HttpResponseMessage.Builder createResponseBuilder(HttpStatusType httpStatusType) {
        int code = httpStatusType.value();
        // Assuming you can create an HttpStatusCode from the code
        HttpStatusCode statusCode = HttpStatusCode.valueOf(code);
        return new MockHttpResponseMessage.Builder(statusCode);
    }

    @Override
    public URI getUri() {
        // Return a dummy URI for testing purposes
        return URI.create("http://localhost:8080");
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }
}
