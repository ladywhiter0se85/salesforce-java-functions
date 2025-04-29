package com.sample.salesforce.functions.handler.mocked;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.sample.salesforce.functions.handler.OpportunityHandlers;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Opportunity;
import com.sample.salesforce.functions.utility.HandlerUtility;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OpportunityHandlerMockedTest extends TestInitializer {
    @Mock
    private OpportunityHandlers mockOpportunityHandlers;
    @Autowired
    ObjectMapper objectMapper;
    private Map<String, String> queryParams;

    @Test
    public void getAllOpportunitiesTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/opportunity/opportunitiesResponse.json");
        List<Opportunity> opportunities = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockOpportunityHandlers.getOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.getOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Opportunity> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(opportunities.get(0).getName(), resp.get(0).getName());
    }

    @Test
    public void getOpportunityByIdTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/opportunity/opportunityResponse.json");
        Opportunity opportunity = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockOpportunityHandlers.getOpportunityById(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.getOpportunityById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Opportunity resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(opportunity.getId(), resp.getId());
    }

    @Test
    public void getOpportunityByIdNoOpportunityIdTest() {
        String msg = "The parameter 'opportunityId' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.getOpportunityById(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.getOpportunityById(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void getOpportunitiesByFilterTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/opportunity/opportunitiesResponse.json");
        List<Opportunity> opportunities = objectMapper.readValue(json, new TypeReference<>() {
        });
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), json);

        when(mockOpportunityHandlers.getOpportunitiesByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.getOpportunitiesByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<Opportunity> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(opportunities.size(), resp.size());
    }

    @Test
    public void getOpportunitiesByFilterNoFilterTest() {
        queryParams.remove("where");
        String msg = "The parameter 'where' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.getOpportunitiesByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.getOpportunitiesByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void getOpportunitiesByFilterNoIsAndTest() {
        queryParams.remove("isAnd");
        String msg = "The parameter 'isAnd' is required.";
        HttpRequestMessage<Void> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), null);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.getOpportunitiesByFilter(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.getOpportunitiesByFilter(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void postOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/postOpportunitiesRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockOpportunityHandlers.postOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.postOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void postOpportunitiesNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.postOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.postOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals("The request body is required.", resp);
    }

    @Test
    public void patchOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/patchOpportunitiesRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockOpportunityHandlers.patchOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.patchOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void patchOpportunitiesNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.patchOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.patchOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void patchOpportunitiesNoIdInBodyTest() {
        String msg = "Each object in a PATCH request must include an 'Id' field.";
        String body = "[{\"attributes\":{\"type\":\"Opportunity\"}, \"Description\":\"CH Test Opportunity Update\"}]";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.patchOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.patchOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void upsertOpportunitiesTest() throws IOException {
        String body = ResponseUtility.readFile("live/opportunity/upsertOpportunitiesRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(200), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(200), opJson);

        when(mockOpportunityHandlers.upsertOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.upsertOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        List<OperationResponse> resp = objectMapper.readValue(response.getBody().toString(), new TypeReference<>() {
        });
        assertNotNull(resp);
        assertEquals(operationResp.get(0).getId(), resp.get(0).getId());
    }

    @Test
    public void upsertOpportunityNoExternalFieldTest() throws IOException {
        String msg = "The parameter 'externalField' is required.";
        String body = ResponseUtility.readFile("live/opportunity/upsertOpportunitiesRequest.json");
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), new HashMap<>(), HttpStatusCode.valueOf(400), body);
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.upsertOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.upsertOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @Test
    public void upsertOpportunitiesNoBodyTest() {
        String msg = "The request body is required.";
        HttpRequestMessage<String> request = HandlerUtility.mockHttpRequestMessage(new HashMap<>(), queryParams, HttpStatusCode.valueOf(400), "");
        ExecutionContext context = HandlerUtility.mockExecutionContext();
        HttpResponseMessage mockResponse = HandlerUtility.mockHttpResponseMessage(HttpStatusCode.valueOf(400), msg);

        when(mockOpportunityHandlers.upsertOpportunities(request, context)).thenReturn(mockResponse);

        HttpResponseMessage response = mockOpportunityHandlers.upsertOpportunities(request, context);
        assertNotNull(response, "Response should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

        String resp = response.getBody().toString();
        assertNotNull(resp);
        assertEquals(msg, resp);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockOpportunityHandlers = mock(OpportunityHandlers.class);

        //Create Query Parameters
        queryParams = new HashMap<>() {{
            put("opportunityId", "mockedOpportunityOneId");
            put("where", "Id:mockedOpportunityOneId");
            put("isAnd", "true");
            put("externalField", "Id");
        }};
    }
}