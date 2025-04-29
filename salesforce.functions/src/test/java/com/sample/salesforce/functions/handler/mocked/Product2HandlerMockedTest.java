package com.sample.salesforce.functions.handler.mocked;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.sample.salesforce.functions.handler.Product2Handlers;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Product2;
import com.sample.salesforce.functions.utility.HandlerUtility;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Product2HandlerMockedTest extends TestInitializer {
    @Mock
    private Product2Handlers mockProduct2Handlers;
    @Autowired
    ObjectMapper objectMapper;
    private Map<String, String> queryParams;

    @Test
    public void getAllProduct2sTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/product2/product2sResponse.json");
        List<Product2> product2s = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockProduct2Handlers.getProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.getProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Product2> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(product2s.get(0).getProductCode(), resp.get(0).getProductCode());
    }

    @Test
    public void getProduct2ByIdTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/product2/product2Response.json");
        Product2 product2 = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockProduct2Handlers.getProduct2ById(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.getProduct2ById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Product2 resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(product2.getName(), resp.getName());
    }

    @Test
    public void getProduct2ByIdNoProduct2IdTest() {
        String msg = "The parameter 'product2Id' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.getProduct2ById(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.getProduct2ById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void getProduct2sByFilterTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/product2/product2sResponse.json");
        List<Product2> product2s = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockProduct2Handlers.getProduct2sByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.getProduct2sByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Product2> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(product2s.size(), resp.size());
    }

    @Test
    public void getProduct2sByFilterNoFilterTest() {
        queryParams.remove("where");
        String msg = "The parameter 'where' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.getProduct2sByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.getProduct2sByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void getProduct2sByFilterNoIsAndTest() {
        queryParams.remove("isAnd");
        String msg = "The parameter 'isAnd' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.getProduct2sByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.getProduct2sByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void postProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/postProduct2sRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockProduct2Handlers.postProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.postProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void postProduct2sNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.postProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.postProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/patchProduct2sRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockProduct2Handlers.patchProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.patchProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void patchProduct2sNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.patchProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.patchProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void patchProduct2sNoIdInBodyTest() {
        String msg = "Each object in a PATCH request must include an 'Id' field.";
        String body = "[{\"attributes\":{\"type\":\"Product2\"}, \"Description\":\"CH Test Product2 Update\"}]";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.patchProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.patchProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void upsertProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/upsertProduct2sRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockProduct2Handlers.upsertProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.upsertProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void upsertProduct2NoExternalFieldTest() throws IOException {
        String msg = "The parameter 'externalField' is required.";
        String body = ResponseUtility.readFile("live/product2/upsertProduct2sRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.upsertProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.upsertProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void upsertProduct2sNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockProduct2Handlers.upsertProduct2s(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockProduct2Handlers.upsertProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockProduct2Handlers = mock(Product2Handlers.class);

        //Create Query Parameters
        queryParams = new HashMap<>() {{
            put("product2Id", "mockedProduct2OneId");
            put("where", "Id:mockedProduct2OneId");
            put("isAnd", "true");
            put("externalField", "Id");
        }};
    }
}