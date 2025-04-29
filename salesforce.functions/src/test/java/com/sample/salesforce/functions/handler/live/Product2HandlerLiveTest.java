package com.sample.salesforce.functions.handler.live;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Product2HandlerLiveTest extends TestInitializer {
    @Autowired
    private Product2Handlers product2Handlers;
    @Autowired
    ObjectMapper objectMapper;
    private Map<String, String> queryParams;

    @Test
    public void getAllProduct2sTest() throws JsonProcessingException {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.getProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Product2> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void getProduct2ByIdTest() throws JsonProcessingException {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.getProduct2ById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Product2 resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void getProduct2ByIdNoProduct2IdTest() {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.getProduct2ById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'product2Id' is required.", resp);
    }

    @Test
    public void getProduct2sByFilterTest() throws JsonProcessingException {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.getProduct2sByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Product2> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void getProduct2sByFilterNoFilterTest() {
        queryParams.remove("where");
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.getProduct2sByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'where' is required.", resp);
    }

    @Test
    public void getProduct2sByFilterNoIsAndTest() {
        queryParams.remove("isAnd");
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.getProduct2sByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'isAnd' is required.", resp);
    }

    @Test
    public void postProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/postProduct2sRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.postProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void postProduct2sNoBodyTest() {
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.postProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/patchProduct2sRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.patchProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void patchProduct2sNoBodyTest() {
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.patchProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchProduct2sNoIdInBodyTest() {
        String body = "[{\"attributes\":{\"type\":\"Product2\"}, \"Description\":\"CH Test Product2 Update\"}]";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.patchProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("Each object in a PATCH request must include an 'Id' field.", resp);
    }

    @Test
    public void upsertProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/upsertProduct2sRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.upsertProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void upsertProduct2NoExternalFieldTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/upsertProduct2sRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.upsertProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'externalField' is required.", resp);
    }

    @Test
    public void upsertProduct2sNoBodyTest() {
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = product2Handlers.upsertProduct2s(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        //Verify Product2Handlers
        assertNotNull(product2Handlers, "Product2Handlers should be initialized.");

        //Create Query Parameters
        queryParams = new HashMap<>() {{
            put("product2Id", "01tgK000001h7QfQAI");
            put("where", "Family:Maintenance,Software");
            put("isAnd", "true");
            put("externalField", "Id");
        }};
    }
}