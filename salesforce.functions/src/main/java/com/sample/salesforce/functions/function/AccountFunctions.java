package com.sample.salesforce.functions.function;

import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Account;
import com.sample.salesforce.functions.query.AccountQueries;
import com.sample.salesforce.functions.service.ApiService;
import com.sample.salesforce.functions.utility.FunctionUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AccountFunctions {

    @Autowired
    private ApiService apiService;
    private static final String S_OBJECT_NAME = "Account";

    public ApiResponse<List<Account>> getAllAccounts() {
        return FunctionUtility.getSObjectResponse(apiService, S_OBJECT_NAME, AccountQueries.getAllAccountsQuery(), Account.class);
    }

    public ApiResponse<Account> getAccountById(String accountId) {
        return FunctionUtility.getSingleSObjectResponse(apiService, S_OBJECT_NAME, AccountQueries.getAccountById(accountId), Account.class);
    }

    public ApiResponse<List<Account>> getAccountsByFilter(String filter, boolean isAnd) {
        return FunctionUtility.getSObjectResponse(apiService, S_OBJECT_NAME, AccountQueries.getAccountsByFilter(filter, isAnd), Account.class);
    }

    public ApiResponse<List<OperationResponse>> postAccounts(String body){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.POST, body, null);
    }

    public ApiResponse<List<OperationResponse>> patchAccounts(String body){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.PATCH, body, null);
    }

    public ApiResponse<List<OperationResponse>> upsertAccounts(String body, String externalField){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.PUT, body, externalField);
    }
}