package com.sample.salesforce.functions.handler.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.sample.salesforce.functions.handler.AccountHandlers;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Account;
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

public class AccountHandlerLiveTest extends TestInitializer {
    @Autowired
    private AccountHandlers accountHandlers;
    @Autowired
    ObjectMapper objectMapper;
    private Map<String, String> queryParams;

    @Test
    public void getAllAccountsTest() throws JsonProcessingException {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.getAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Account> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void getAccountByIdTest() throws JsonProcessingException {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.getAccountById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Account resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void getAccountByIdNoAccountIdTest() {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.getAccountById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'accountId' is required.", resp);
    }

    @Test
    public void getAccountsByFilterTest() throws JsonProcessingException {
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.getAccountsByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Account> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void getAccountsByFilterNoFilterTest() {
        queryParams.remove("where");
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.getAccountsByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'where' is required.", resp);
    }

    @Test
    public void getAccountsByFilterNoIsAndTest() {
        queryParams.remove("isAnd");
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.getAccountsByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'isAnd' is required.", resp);
    }

    @Test
    public void postAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/postAccountsRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.postAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void postAccountsNoBodyTest() {
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.postAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/patchAccountsRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.patchAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void patchAccountsNoBodyTest() {
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.patchAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchAccountsNoIdInBodyTest() {
        String body = "[{\"attributes\":{\"type\":\"Account\"}, \"Description\":\"CH Test Account Update\"}]";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.patchAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("Each object in a PATCH request must include an 'Id' field.", resp);
    }

    @Test
    public void upsertAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/upsertAccountsRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.upsertAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
    }

    @Test
    public void upsertAccountNoExternalFieldTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/upsertAccountsRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.upsertAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The parameter 'externalField' is required.", resp);
    }

    @Test
    public void upsertAccountsNoBodyTest() {
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();

        HttpResponseMessage response = accountHandlers.upsertAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        //Verify AccountHandlers
        assertNotNull(accountHandlers, "AccountHandlers should be initialized.");

        //Create Query Parameters
        queryParams = new HashMap<>() {{
            put("accountId", "001gK000002OgxyQAC");
            put("where", "BillingState:CA&BillingCountryCode:US");
            put("isAnd", "true");
            put("externalField", "Id");
        }};
    }
}