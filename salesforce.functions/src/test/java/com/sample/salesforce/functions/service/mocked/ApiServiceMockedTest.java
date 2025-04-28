package com.sample.salesforce.functions.service.mocked;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.service.ApiService;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ApiServiceMockedTest extends TestInitializer {
    @Mock
    private ApiService mockApiService;
    private String accessToken;
    private String operationResponse;

    @Test
    public void getAccountQueryTest() throws ApiResponseException, JsonProcessingException {
        String query = "SELECT Id, Name FROM Account LIMIT 1";
        String queryResp = "{\"totalSize\":1,\"done\":true,\"records\":[{\"attributes\":{\"type\":\"Account\",\"url\":\"/services/data/v58.0/sobjects/Account/001gK000002OgxpQAC\"},\"Id\":\"001gK000002OgxpQAC\",\"Name\":\"Edge Communications\"}]}";

        when(mockApiService.getQuery(accessToken, "Account", query)).thenReturn(queryResp);

        String resp = mockApiService.getQuery(accessToken, "Account", query);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(queryResp, resp);
    }

    @Test
    public void getDescribeTest() throws ApiResponseException, IOException {
        String describeResp = ResponseUtility.readFile("mocked/describe/describeSFResponse.json");

        when(mockApiService.getDescribe(accessToken, "Account")).thenReturn(describeResp);

        String resp = mockApiService.getDescribe(accessToken, "Account");
        assertNotNull(resp, "Response should not be null.");
        assertEquals(describeResp, resp);
    }

    @Test
    public void postRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Name\":\"CH Test Account 6\",\"Phone\":\"555-444-5855\",\"Description\":\"CH Test Account 6 Creation\"}]";

        when(mockApiService.postRequest(accessToken, "Account", body)).thenReturn(operationResponse);

        String resp = mockApiService.postRequest(accessToken, "Account", body);
        assertNotNull(resp);
        assertEquals(operationResponse, resp);
    }

    @Test
    public void patchRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Id\": \"001gK00000391O9QAI\", \"Description\":\"CH Test Account Update\"}]";

        when(mockApiService.patchRequest(accessToken, "Account", body)).thenReturn(operationResponse);

        String resp = mockApiService.patchRequest(accessToken, "Account", body);
        assertNotNull(resp);
        assertEquals(operationResponse, resp);
    }

    @Test
    public void upsertUpdateRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Id\": \"001gK00000391O9QAI\", \"Description\":\"CH Test Account Upsert\"}]";

        when(mockApiService.upsertRequest(accessToken, "Account", body, "Id")).thenReturn(operationResponse);

        String resp = mockApiService.upsertRequest(accessToken, "Account", body, "Id");
        assertNotNull(resp);
        assertEquals(operationResponse, resp);
    }

    @Test
    public void upsertUpsertRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Name\": \"CH Test Account 3\", \"Description\":\"CH Test Account New\"}]";

        when(mockApiService.upsertRequest(accessToken, "Account", body, "Id")).thenReturn(operationResponse);

        String resp = mockApiService.upsertRequest(accessToken, "Account", body, "Id");
        assertNotNull(resp);
        assertEquals(operationResponse, resp);
    }

    @Test
    public void upsertBothRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Id\": \"001gK00000391O9QAI\", \"Description\":\"CH Test Account Upsert\"},{\"attributes\":{\"type\":\"Account\"},\"Name\": \"CH Test Account 4\", \"Description\":\"CH Test Account 4 New\"}]";

        when(mockApiService.upsertRequest(accessToken, "Account", body, "Id")).thenReturn(operationResponse);

        String resp = mockApiService.upsertRequest(accessToken, "Account", body, "Id");
        assertNotNull(resp);
        assertEquals(operationResponse, resp);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        accessToken = "mockedAccessToken";
        operationResponse = "[{\"id\":\"mockedAccountId\",\"success\":true,\"errors\":[]}]";
        mockApiService = Mockito.mock(ApiService.class);
    }
}