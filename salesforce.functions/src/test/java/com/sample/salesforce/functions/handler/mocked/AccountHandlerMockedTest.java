package com.sample.salesforce.functions.handler.mocked;

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

public class AccountHandlerMockedTest extends TestInitializer {
    @Mock
    private AccountHandlers mockAccountHandlers;
    @Autowired
    ObjectMapper objectMapper;
    private Map<String, String> queryParams;

    @Test
    public void getAllAccountsTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/account/accountsResponse.json");
        List<Account> accounts = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockAccountHandlers.getAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.getAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Account> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(accounts.get(0).getAccountNumber(), resp.get(0).getAccountNumber());
    }

    @Test
    public void getAccountByIdTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/account/accountResponse.json");
        Account account = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockAccountHandlers.getAccountById(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.getAccountById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Account resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(account.getBillingAddress().getCity(), resp.getBillingAddress().getCity());
    }

    @Test
    public void getAccountByIdNoAccountIdTest() {
        String msg = "The parameter 'accountId' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.getAccountById(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.getAccountById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void getAccountsByFilterTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/account/accountsResponse.json");
        List<Account> accounts = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockAccountHandlers.getAccountsByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.getAccountsByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Account> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(accounts.get(0).getAccountNumber(), resp.get(0).getAccountNumber());
    }

    @Test
    public void getAccountsByFilterNoFilterTest() {
        queryParams.remove("where");
        String msg = "The parameter 'where' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.getAccountsByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.getAccountsByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void getAccountsByFilterNoIsAndTest() {
        queryParams.remove("isAnd");
        String msg = "The parameter 'isAnd' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.getAccountsByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.getAccountsByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void postAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/postAccountsRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockAccountHandlers.postAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.postAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void postAccountsNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.postAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.postAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/patchAccountsRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockAccountHandlers.patchAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.patchAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void patchAccountsNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.patchAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.patchAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void patchAccountsNoIdInBodyTest() {
        String msg = "Each object in a PATCH request must include an 'Id' field.";
        String body = "[{\"attributes\":{\"type\":\"Account\"}, \"Description\":\"CH Test Account Update\"}]";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.patchAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.patchAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void upsertAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/upsertAccountsRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockAccountHandlers.upsertAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.upsertAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void upsertAccountNoExternalFieldTest() throws IOException {
        String msg = "The parameter 'externalField' is required.";
        String body = ResponseUtility.readFile("live/account/upsertAccountsRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.upsertAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.upsertAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void upsertAccountsNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockAccountHandlers.upsertAccounts(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockAccountHandlers.upsertAccounts(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockAccountHandlers = mock(AccountHandlers.class);

        //Create Query Parameters
        queryParams = new HashMap<>() {{
            put("accountId", "mockedAccountOneId");
            put("where", "Id:mockedAccountOneId");
            put("isAnd", "true");
            put("externalField", "Id");
        }};
    }
}