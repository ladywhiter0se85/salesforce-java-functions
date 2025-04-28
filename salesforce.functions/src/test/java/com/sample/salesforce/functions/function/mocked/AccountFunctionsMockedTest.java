package com.sample.salesforce.functions.function.mocked;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.salesforce.functions.function.AccountFunctions;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Account;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountFunctionsMockedTest extends TestInitializer {
    @Mock
    private AccountFunctions mockAccountFunctions;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getAllAccountsTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/account/accountsResponse.json");
        List<Account> accounts = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockAccountFunctions.getAllAccounts()).thenReturn(new ApiResponse<>(accounts));

        ApiResponse<List<Account>> resp = mockAccountFunctions.getAllAccounts();
        assertNotNull(resp, "Response should not be null.");
        assertEquals("Burlington", resp.getData().get(1).getBillingAddress().getCity());
    }

    @Test
    public void getAccountByIdTest() throws IOException {
        String accountId = "mockedAccountOneId";
        String json = ResponseUtility.readFile("mocked/account/accountResponse.json");
        Account account = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockAccountFunctions.getAccountById(accountId)).thenReturn(new ApiResponse<>(account));

        ApiResponse<Account> resp = mockAccountFunctions.getAccountById(accountId);
        assertNotNull(resp, "Response should not be null.");
        assertEquals("Mocked Account One", resp.getData().getName());
    }

    @Test
    public void getAccountsByFilterOneToOneAndTest() throws IOException {
        String filter = "Type:Customer - Direct&BillingCountryCode:US";
        String json = ResponseUtility.readFile("mocked/account/accountsResponse.json");
        List<Account> accounts = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockAccountFunctions.getAccountsByFilter(filter, true)).thenReturn(new ApiResponse<>(accounts));

        ApiResponse<List<Account>> resp = mockAccountFunctions.getAccountsByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void getAccountsByFilterOneToManyAndTest() throws IOException {
        String filter = "BillingState:TX,NC";
        String json = ResponseUtility.readFile("mocked/account/accountsResponse.json");
        List<Account> accounts = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockAccountFunctions.getAccountsByFilter(filter, true)).thenReturn(new ApiResponse<>(accounts));

        ApiResponse<List<Account>> resp = mockAccountFunctions.getAccountsByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void getAccountsByFilterManyToOneOrTest() throws IOException {
        String filter = "Name:Mocked Account One,Mocked Account Two&Id:mockedAccountTwoId";
        String json = ResponseUtility.readFile("mocked/account/accountsResponse.json");
        List<Account> accounts = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockAccountFunctions.getAccountsByFilter(filter, false)).thenReturn(new ApiResponse<>(accounts));

        ApiResponse<List<Account>> resp = mockAccountFunctions.getAccountsByFilter(filter, false);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void postAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/postAccountsRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockAccountFunctions.postAccounts(body)).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockAccountFunctions.postAccounts(body);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @Test
    public void patchAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/patchAccountsRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockAccountFunctions.patchAccounts(body)).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockAccountFunctions.patchAccounts(body);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @Test
    public void upsertAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/upsertAccountsRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockAccountFunctions.upsertAccounts(body, "Id")).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockAccountFunctions.upsertAccounts(body, "Id");
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        mockAccountFunctions = mock(AccountFunctions.class);
    }
}