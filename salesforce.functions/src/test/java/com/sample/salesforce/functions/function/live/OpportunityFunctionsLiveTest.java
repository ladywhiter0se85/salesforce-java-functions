package com.sample.salesforce.functions.function.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.salesforce.functions.function.OpportunityFunctions;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Opportunity;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpportunityFunctionsLiveTest extends TestInitializer {
    @Autowired
    private OpportunityFunctions opportunityFunctions;

    @Test
    public void getAllOpportunitiesTest() {
        ApiResponse<List<Opportunity>> resp = opportunityFunctions.getAllOpportunities();
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getOpportunityByIdTest() {
        ApiResponse<Opportunity> resp = opportunityFunctions.getOpportunityById("006gK000000J5DhQAK");
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getOpportunitiesByFilterOneToOneAndTest() {
        String filter = "StageName:Closed Won&Type:New Customer";
        ApiResponse<List<Opportunity>> resp = opportunityFunctions.getOpportunitiesByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getOpportunitiesByFilterOneToManyAndTest() {
        String filter = "StageName:Value Proposition,Id. Decision Makers";
        ApiResponse<List<Opportunity>> resp = opportunityFunctions.getOpportunitiesByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getOpportunitiesByFilterManyToOneOrTest() {
        String filter = "Name:Grand Hotels SLA,Edge SLA,United Oil SLA&AccountId:001gK000002OgxzQAC";
        ApiResponse<List<Opportunity>> resp = opportunityFunctions.getOpportunitiesByFilter(filter, false);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void postOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/postOpportunitiesRequest.json");
        ApiResponse<List<OperationResponse>> resp = opportunityFunctions.postOpportunities(body);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void patchOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/patchOpportunitiesRequest.json");
        ApiResponse<List<OperationResponse>> resp = opportunityFunctions.patchOpportunities(body);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void upsertOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/upsertOpportunitiesRequest.json");
        ApiResponse<List<OperationResponse>> resp = opportunityFunctions.upsertOpportunities(body, "Id");
        assertNotNull(resp, "Response should not be null.");
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        //Verify OpportunityFunction
        assertNotNull(opportunityFunctions, "OpportunityFunction should be initialized.");
    }
}