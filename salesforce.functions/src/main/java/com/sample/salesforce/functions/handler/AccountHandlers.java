package com.sample.salesforce.functions.handler;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.function.AccountFunctions;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Account;
import com.sample.salesforce.functions.utility.ResponseUtility;
import com.sample.salesforce.functions.validation.BodyValidation;
import com.sample.salesforce.functions.validation.ParameterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountHandlers {
    @Autowired
    AccountFunctions accountFunctions;

    @FunctionName("getAccounts")
    public HttpResponseMessage getAccounts(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("AccountFunctions: Get All Salesforce Accounts Request");
        ApiResponse<List<Account>> accountsResponse = accountFunctions.getAllAccounts();
        HttpResponseMessage response = ResponseUtility.buildGetResponse(request, accountsResponse);
        context.getLogger().info("AccountFunctions: Get All Salesforce Accounts Request Complete");
        return response;
    }

    @FunctionName("getAccountById")
    public HttpResponseMessage getAccountById(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("AccountFunctions: Get Salesforce Account By Id Request");
        try {
            String accountId = ParameterValidation.validateStringParam(request.getQueryParameters().get("accountId"), "accountId");
            ApiResponse<Account> accountsResponse = accountFunctions.getAccountById(accountId);
            HttpResponseMessage response = ResponseUtility.buildGetResponse(request, accountsResponse);
            context.getLogger().info("AccountFunctions: Get Salesforce Account By Id Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildGetResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("getAccountsByFilter")
    public HttpResponseMessage getAccountsByFilter(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("AccountFunctions: Get Salesforce Accounts By Filter Request");
        try {
            String filter = ParameterValidation.validateStringParam(request.getQueryParameters().get("where"), "where");
            boolean cond = ParameterValidation.validateBooleanParam(request.getQueryParameters().get("isAnd"), "isAnd");
            ApiResponse<List<Account>> accountsResponse = accountFunctions.getAccountsByFilter(filter, cond);
            HttpResponseMessage response = ResponseUtility.buildGetResponse(request, accountsResponse);
            context.getLogger().info("AccountFunctions: Get Salesforce Accounts By Filter Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildGetResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("postAccounts")
    public HttpResponseMessage postAccounts(
            @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("AccountFunctions: Post Salesforce Accounts Request");
        try {
            String body = BodyValidation.validate(request.getBody());
            ApiResponse<List<OperationResponse>> accountsResponse = accountFunctions.postAccounts(body);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, accountsResponse);
            context.getLogger().info("AccountFunctions: Post Salesforce Accounts Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("patchAccounts")
    public HttpResponseMessage patchAccounts(
            @HttpTrigger(name = "request", methods = {HttpMethod.PATCH}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("AccountFunctions: Patch Salesforce Accounts Request");
        try {
            String body = BodyValidation.validateAndCheckIds(request.getBody());
            ApiResponse<List<OperationResponse>> accountsResponse = accountFunctions.patchAccounts(body);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, accountsResponse);
            context.getLogger().info("AccountFunctions: Patch Salesforce Accounts Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("upsertAccounts")
    public HttpResponseMessage upsertAccounts(
            @HttpTrigger(name = "request", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("AccountFunctions: Upsert Salesforce Accounts Request");
        try {
            String externalField = ParameterValidation.validateStringParam(request.getQueryParameters().get("externalField"), "externalField");
            String body = BodyValidation.validate(request.getBody());
            ApiResponse<List<OperationResponse>> accountsResponse = accountFunctions.upsertAccounts(body, externalField);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, accountsResponse);
            context.getLogger().info("AccountFunctions: Upsert Salesforce Accounts Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }
}
