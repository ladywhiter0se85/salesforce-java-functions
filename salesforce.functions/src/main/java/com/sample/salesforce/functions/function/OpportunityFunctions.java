package com.sample.salesforce.functions.function;

import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Opportunity;
import com.sample.salesforce.functions.query.OpportunityQueries;
import com.sample.salesforce.functions.service.ApiService;
import com.sample.salesforce.functions.utility.FunctionUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpportunityFunctions {

    @Autowired
    private ApiService apiService;
    private static final String S_OBJECT_NAME = "Opportunity";

    public ApiResponse<List<Opportunity>> getAllOpportunities() {
        return FunctionUtility.getSObjectResponse(apiService, S_OBJECT_NAME, OpportunityQueries.getAllOpportunitiesQuery(), Opportunity.class);
    }

    public ApiResponse<Opportunity> getOpportunityById(String opportunityId) {
        return FunctionUtility.getSingleSObjectResponse(apiService, S_OBJECT_NAME, OpportunityQueries.getOpportunityById(opportunityId), Opportunity.class);
    }

    public ApiResponse<List<Opportunity>> getOpportunitiesByFilter(String filter, boolean isAnd) {
        return FunctionUtility.getSObjectResponse(apiService, S_OBJECT_NAME, OpportunityQueries.getOpportunitiesByFilter(filter, isAnd), Opportunity.class);
    }

    public ApiResponse<List<OperationResponse>> postOpportunities(String body){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.POST, body, null);
    }

    public ApiResponse<List<OperationResponse>> patchOpportunities(String body){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.PATCH, body, null);
    }

    public ApiResponse<List<OperationResponse>> upsertOpportunities(String body, String externalField){
        return FunctionUtility.processSObjectOperation(apiService, S_OBJECT_NAME, HttpMethod.PUT, body, externalField);
    }
}