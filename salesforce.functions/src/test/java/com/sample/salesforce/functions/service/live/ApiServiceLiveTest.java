package com.sample.salesforce.functions.service.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.service.ApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApiServiceLiveTest extends TestInitializer {
    @Autowired
    private ApiService apiService;
    private String accessToken;

    @Test
    public void getAccountQueryTest() throws ApiResponseException, JsonProcessingException {
        String query = "SELECT Id, Name FROM Account LIMIT 1";
        String resp = apiService.getQuery(accessToken, "Account", query);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getDescribeTest() throws ApiResponseException, JsonProcessingException {
        String resp = apiService.getDescribe(accessToken, "Account");
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void postRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Name\":\"CH Test Account 6\",\"Phone\":\"555-444-5855\",\"Description\":\"CH Test Account 6 Creation\"}]";
        String resp = apiService.postRequest(accessToken, "Account", body);
        assertNotNull(resp);
    }

    @Test
    public void patchRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Id\": \"001gK00000391O9QAI\", \"Description\":\"CH Test Account Update\"}]";
        String resp = apiService.patchRequest(accessToken, "Account", body);
        assertNotNull(resp);
    }

    @Test
    public void upsertUpdateRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Id\": \"001gK00000391O9QAI\", \"Description\":\"CH Test Account Upsert\"}]";
        String resp = apiService.upsertRequest(accessToken, "Account", body, "Id");
        assertNotNull(resp);
    }

    @Test
    public void upsertUpsertRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Name\": \"CH Test Account 3\", \"Description\":\"CH Test Account New\"}]";
        String resp = apiService.upsertRequest(accessToken, "Account", body, "Id");
        assertNotNull(resp);
    }

    @Test
    public void upsertBothRequestTest() throws ApiResponseException, JsonProcessingException {
        String body = "[{\"attributes\":{\"type\":\"Account\"},\"Id\": \"001gK00000391O9QAI\", \"Description\":\"CH Test Account Upsert\"},{\"attributes\":{\"type\":\"Account\"},\"Name\": \"CH Test Account 4\", \"Description\":\"CH Test Account 4 New\"}]";
        String resp = apiService.upsertRequest(accessToken, "Account", body, "Id");
        assertNotNull(resp);
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        //Verify ApiService
        assertNotNull(apiService, "ApiService should be initialized.");

        accessToken = apiService.getAccessToken();
        assertNotNull(accessToken, "Access token should be initialized.");
    }
}