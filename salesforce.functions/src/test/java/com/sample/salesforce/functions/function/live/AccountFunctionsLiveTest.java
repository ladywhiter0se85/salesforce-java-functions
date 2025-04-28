package com.sample.salesforce.functions.function.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.salesforce.functions.function.AccountFunctions;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Account;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountFunctionsLiveTest extends TestInitializer {
    @Autowired
    private AccountFunctions accountFunctions;

    @Test
    public void getAllAccountsTest() {
        ApiResponse<List<Account>> resp = accountFunctions.getAllAccounts();
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getAccountByIdTest() {
        ApiResponse<Account> resp = accountFunctions.getAccountById("001gK000002OgxyQAC");
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getAccountsByFilterOneToOneAndTest() {
        String filter = "BillingState:CA&BillingCountryCode:US";
        ApiResponse<List<Account>> resp = accountFunctions.getAccountsByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getAccountsByFilterOneToManyAndTest() {
        String filter = "BillingState:CA,British Columbia";
        ApiResponse<List<Account>> resp = accountFunctions.getAccountsByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getAccountsByFilterManyToOneOrTest() {
        String filter = "Name:Pyramid Construction Inc.,University of Arizona&Id:001gK000002OgxzQAC";
        ApiResponse<List<Account>> resp = accountFunctions.getAccountsByFilter(filter, false);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void postAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/postAccountsRequest.json");
        ApiResponse<List<OperationResponse>> resp = accountFunctions.postAccounts(body);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void patchAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/patchAccountsRequest.json");
        ApiResponse<List<OperationResponse>> resp = accountFunctions.patchAccounts(body);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void upsertAccountsTest() throws IOException {
        String body = ResponseUtility.readFile("live/account/upsertAccountsRequest.json");
        ApiResponse<List<OperationResponse>> resp = accountFunctions.upsertAccounts(body, "Id");
        assertNotNull(resp, "Response should not be null.");
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        //Verify AccountFunction
        assertNotNull(accountFunctions, "AccountFunction should be initialized.");
    }
}