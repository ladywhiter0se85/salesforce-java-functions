package com.sample.salesforce.functions.function;

import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Product2;
import com.sample.salesforce.functions.query.Product2Queries;
import com.sample.salesforce.functions.service.ApiService;
import com.sample.salesforce.functions.utility.FunctionUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Product2Functions {

    @Autowired
    private ApiService apiService;
    private static final String S_OBJECT_NAME = "Product2";

    public ApiResponse<List<Product2>> getAllProduct2s() {
        return FunctionUtility.getSObjectResponse(apiService, S_OBJECT_NAME, Product2Queries.getAllProduct2sQuery(), Product2.class);
    }

    public ApiResponse<Product2> getProduct2ById(String product2Id) {
        return FunctionUtility.getSingleSObjectResponse(apiService, S_OBJECT_NAME, Product2Queries.getProduct2ById(product2Id), Product2.class);
    }

    public ApiResponse<List<Product2>> getProduct2sByFilter(String filter, boolean isAnd) {
        return FunctionUtility.getSObjectResponse(apiService, S_OBJECT_NAME, Product2Queries.getProduct2sByFilter(filter, isAnd), Product2.class);
    }

    public ApiResponse<List<OperationResponse>> postProduct2s(String body){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.POST, body, null);
    }

    public ApiResponse<List<OperationResponse>> patchProduct2s(String body){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.PATCH, body, null);
    }

    public ApiResponse<List<OperationResponse>> upsertProduct2s(String body, String externalField){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.PUT, body, externalField);
    }
}