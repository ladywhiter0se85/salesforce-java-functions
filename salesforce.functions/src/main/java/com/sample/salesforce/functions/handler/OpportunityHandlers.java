package com.sample.salesforce.functions.handler;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.function.OpportunityFunctions;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Opportunity;
import com.sample.salesforce.functions.utility.ResponseUtility;
import com.sample.salesforce.functions.validation.BodyValidation;
import com.sample.salesforce.functions.validation.ParameterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpportunityHandlers {
    @Autowired
    OpportunityFunctions opportunityFunctions;

    @FunctionName("getOpportunities")
    public HttpResponseMessage getOpportunities(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("OpportunityFunctions: Get All Salesforce Opportunities Request");
        ApiResponse<List<Opportunity>> opportunitiesResponse = opportunityFunctions.getAllOpportunities();
        HttpResponseMessage response = ResponseUtility.buildGetResponse(request, opportunitiesResponse);
        context.getLogger().info("OpportunityFunctions: Get All Salesforce Opportunities Request Complete");
        return response;
    }

    @FunctionName("getOpportunityById")
    public HttpResponseMessage getOpportunityById(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("OpportunityFunctions: Get Salesforce Opportunity By Id Request");
        try {
            String opportunityId = ParameterValidation.validateStringParam(request.getQueryParameters().get("opportunityId"), "opportunityId");
            ApiResponse<Opportunity> opportunitiesResponse = opportunityFunctions.getOpportunityById(opportunityId);
            HttpResponseMessage response = ResponseUtility.buildGetResponse(request, opportunitiesResponse);
            context.getLogger().info("OpportunityFunctions: Get Salesforce Opportunity By Id Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildGetResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("getOpportunitiesByFilter")
    public HttpResponseMessage getOpportunitiesByFilter(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("OpportunityFunctions: Get Salesforce Opportunities By Filter Request");
        try {
            String filter = ParameterValidation.validateStringParam(request.getQueryParameters().get("where"), "where");
            boolean cond = ParameterValidation.validateBooleanParam(request.getQueryParameters().get("isAnd"), "isAnd");
            ApiResponse<List<Opportunity>> opportunitiesResponse = opportunityFunctions.getOpportunitiesByFilter(filter, cond);
            HttpResponseMessage response = ResponseUtility.buildGetResponse(request, opportunitiesResponse);
            context.getLogger().info("OpportunityFunctions: Get Salesforce Opportunities By Filter Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildGetResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("postOpportunities")
    public HttpResponseMessage postOpportunities(
            @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("OpportunityFunctions: Post Salesforce Opportunities Request");
        try {
            String body = BodyValidation.validate(request.getBody());
            ApiResponse<List<OperationResponse>> opportunitiesResponse = opportunityFunctions.postOpportunities(body);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, opportunitiesResponse);
            context.getLogger().info("OpportunityFunctions: Post Salesforce Opportunities Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("patchOpportunities")
    public HttpResponseMessage patchOpportunities(
            @HttpTrigger(name = "request", methods = {HttpMethod.PATCH}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("OpportunityFunctions: Patch Salesforce Opportunities Request");
        try {
            String body = BodyValidation.validateAndCheckIds(request.getBody());
            ApiResponse<List<OperationResponse>> opportunitiesResponse = opportunityFunctions.patchOpportunities(body);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, opportunitiesResponse);
            context.getLogger().info("OpportunityFunctions: Patch Salesforce Opportunities Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("upsertOpportunities")
    public HttpResponseMessage upsertOpportunities(
            @HttpTrigger(name = "request", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("OpportunityFunctions: Upsert Salesforce Opportunities Request");
        try {
            String externalField = ParameterValidation.validateStringParam(request.getQueryParameters().get("externalField"), "externalField");
            String body = BodyValidation.validate(request.getBody());
            ApiResponse<List<OperationResponse>> opportunitiesResponse = opportunityFunctions.upsertOpportunities(body, externalField);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, opportunitiesResponse);
            context.getLogger().info("OpportunityFunctions: Upsert Salesforce Opportunities Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }
}
