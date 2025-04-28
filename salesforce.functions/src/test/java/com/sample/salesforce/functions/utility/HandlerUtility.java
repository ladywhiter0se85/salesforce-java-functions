package com.sample.salesforce.functions.utility;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpStatus;
import com.sample.salesforce.functions.utility.mocked.responses.MockHttpRequestMessage;
import com.sample.salesforce.functions.utility.mocked.responses.MockHttpResponseMessage;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HandlerUtility {
    public static <T> MockHttpRequestMessage<T> mockHttpRequestMessage(Map<String, String> headers, Map<String, String> query, HttpStatusCode status, T body) {
        MockHttpRequestMessage<T> mockRequest = mock(MockHttpRequestMessage.class);
        when(mockRequest.getHeaders()).thenReturn(headers);
        when(mockRequest.getQueryParameters()).thenReturn(query);
        when(mockRequest.getBody()).thenReturn(body);

        // Mock the createResponseBuilder to return a new MockHttpResponseMessage.Builder
        when(mockRequest.createResponseBuilder(any(HttpStatus.class)))
                .thenReturn(new MockHttpResponseMessage.Builder(status));  // Set expected Status

        return mockRequest;
    }

    public static <T> MockHttpResponseMessage mockHttpResponseMessage(HttpStatusCode statusCode, T body) {
        MockHttpResponseMessage mockedResponse = mock(MockHttpResponseMessage.class);
        when(mockedResponse.getBody()).thenReturn(body);
        when(mockedResponse.getStatusCode()).thenReturn(statusCode.value());
        return mockedResponse;
    }

    public static ExecutionContext mockExecutionContext() {
        ExecutionContext context = mock(ExecutionContext.class);
        when(context.getLogger()).thenReturn(mock(java.util.logging.Logger.class));
        return context;
    }
}
