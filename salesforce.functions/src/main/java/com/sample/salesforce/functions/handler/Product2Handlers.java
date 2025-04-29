package com.sample.salesforce.functions.handler;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.function.Product2Functions;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Product2;
import com.sample.salesforce.functions.utility.ResponseUtility;
import com.sample.salesforce.functions.validation.BodyValidation;
import com.sample.salesforce.functions.validation.ParameterValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Product2Handlers {
    @Autowired
    Product2Functions product2Functions;

    @FunctionName("getProduct2s")
    public HttpResponseMessage getProduct2s(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("Product2Functions: Get All Salesforce Product2s Request");
        ApiResponse<List<Product2>> product2sResponse = product2Functions.getAllProduct2s();
        HttpResponseMessage response = ResponseUtility.buildGetResponse(request, product2sResponse);
        context.getLogger().info("Product2Functions: Get All Salesforce Product2s Request Complete");
        return response;
    }

    @FunctionName("getProduct2ById")
    public HttpResponseMessage getProduct2ById(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("Product2Functions: Get Salesforce Product2 By Id Request");
        try {
            String product2Id = ParameterValidation.validateStringParam(request.getQueryParameters().get("product2Id"), "product2Id");
            ApiResponse<Product2> product2sResponse = product2Functions.getProduct2ById(product2Id);
            HttpResponseMessage response = ResponseUtility.buildGetResponse(request, product2sResponse);
            context.getLogger().info("Product2Functions: Get Salesforce Product2 By Id Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildGetResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("getProduct2sByFilter")
    public HttpResponseMessage getProduct2sByFilter(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Void> request,
            ExecutionContext context) {
        context.getLogger().info("Product2Functions: Get Salesforce Product2s By Filter Request");
        try {
            String filter = ParameterValidation.validateStringParam(request.getQueryParameters().get("where"), "where");
            boolean cond = ParameterValidation.validateBooleanParam(request.getQueryParameters().get("isAnd"), "isAnd");
            ApiResponse<List<Product2>> product2sResponse = product2Functions.getProduct2sByFilter(filter, cond);
            HttpResponseMessage response = ResponseUtility.buildGetResponse(request, product2sResponse);
            context.getLogger().info("Product2Functions: Get Salesforce Product2s By Filter Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildGetResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("postProduct2s")
    public HttpResponseMessage postProduct2s(
            @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("Product2Functions: Post Salesforce Product2s Request");
        try {
            String body = BodyValidation.validate(request.getBody());
            ApiResponse<List<OperationResponse>> product2sResponse = product2Functions.postProduct2s(body);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, product2sResponse);
            context.getLogger().info("Product2Functions: Post Salesforce Product2s Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("patchProduct2s")
    public HttpResponseMessage patchProduct2s(
            @HttpTrigger(name = "request", methods = {HttpMethod.PATCH}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("Product2Functions: Patch Salesforce Product2s Request");
        try {
            String body = BodyValidation.validateAndCheckIds(request.getBody());
            ApiResponse<List<OperationResponse>> product2sResponse = product2Functions.patchProduct2s(body);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, product2sResponse);
            context.getLogger().info("Product2Functions: Patch Salesforce Product2s Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }

    @FunctionName("upsertProduct2s")
    public HttpResponseMessage upsertProduct2s(
            @HttpTrigger(name = "request", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            ExecutionContext context) {
        context.getLogger().info("Product2Functions: Upsert Salesforce Product2s Request");
        try {
            String externalField = ParameterValidation.validateStringParam(request.getQueryParameters().get("externalField"), "externalField");
            String body = BodyValidation.validate(request.getBody());
            ApiResponse<List<OperationResponse>> product2sResponse = product2Functions.upsertProduct2s(body, externalField);
            HttpResponseMessage response = ResponseUtility.buildOperationResponse(request, product2sResponse);
            context.getLogger().info("Product2Functions: Upsert Salesforce Product2s Request Complete");
            return response;
        } catch (ApiResponseException e) {
            return ResponseUtility.buildOperationResponse(request, e.getApiResponse());
        }
    }
}
