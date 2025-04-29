package com.sample.salesforce.functions.function.mocked;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.salesforce.functions.function.OpportunityFunctions;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Opportunity;
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

public class OpportunityFunctionsMockedTest extends TestInitializer {
    @Mock
    private OpportunityFunctions mockOpportunityFunctions;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getAllOpportunitiesTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/opportunity/opportunitiesResponse.json");
        List<Opportunity> opportunities = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.getAllOpportunities()).thenReturn(new ApiResponse<>(opportunities));

        ApiResponse<List<Opportunity>> resp = mockOpportunityFunctions.getAllOpportunities();
        assertNotNull(resp, "Response should not be null.");
        assertEquals("Mocked Opportunity Two", resp.getData().get(1).getName());
    }

    @Test
    public void getOpportunityByIdTest() throws IOException {
        String opportunityId = "mockedOpportunityIdOne";
        String json = ResponseUtility.readFile("mocked/opportunity/opportunityResponse.json");
        Opportunity opportunity = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.getOpportunityById(opportunityId)).thenReturn(new ApiResponse<>(opportunity));

        ApiResponse<Opportunity> resp = mockOpportunityFunctions.getOpportunityById(opportunityId);
        assertNotNull(resp, "Response should not be null.");
        assertEquals("Mocked Opportunity One", resp.getData().getName());
    }

    @Test
    public void getOpportunitiesByFilterOneToOneAndTest() throws IOException {
        String filter = "StageName:Qualification&Type:Existing Customer - Upgrade";
        String json = ResponseUtility.readFile("mocked/opportunity/opportunitiesResponse.json");
        List<Opportunity> opportunities = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.getOpportunitiesByFilter(filter, true)).thenReturn(new ApiResponse<>(opportunities));

        ApiResponse<List<Opportunity>> resp = mockOpportunityFunctions.getOpportunitiesByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void getOpportunitiesByFilterOneToManyAndTest() throws IOException {
        String filter = "StageName:Qualification,Negotiation/Review";
        String json = ResponseUtility.readFile("mocked/opportunity/opportunitiesResponse.json");
        List<Opportunity> opportunities = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.getOpportunitiesByFilter(filter, true)).thenReturn(new ApiResponse<>(opportunities));

        ApiResponse<List<Opportunity>> resp = mockOpportunityFunctions.getOpportunitiesByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void getOpportunitiesByFilterManyToOneOrTest() throws IOException {
        String filter = "Name:Mocked Opportunity One,Mocked Opportunity Two&Id:mockedOpportunityTwoId";
        String json = ResponseUtility.readFile("mocked/opportunity/opportunitiesResponse.json");
        List<Opportunity> opportunities = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.getOpportunitiesByFilter(filter, false)).thenReturn(new ApiResponse<>(opportunities));

        ApiResponse<List<Opportunity>> resp = mockOpportunityFunctions.getOpportunitiesByFilter(filter, false);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void postOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/postOpportunitiesRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.postOpportunities(body)).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockOpportunityFunctions.postOpportunities(body);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @Test
    public void patchOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/patchOpportunitiesRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.patchOpportunities(body)).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockOpportunityFunctions.patchOpportunities(body);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @Test
    public void upsertOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/upsertOpportunitiesRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockOpportunityFunctions.upsertOpportunities(body, "Id")).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockOpportunityFunctions.upsertOpportunities(body, "Id");
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        mockOpportunityFunctions = mock(OpportunityFunctions.class);
    }
}