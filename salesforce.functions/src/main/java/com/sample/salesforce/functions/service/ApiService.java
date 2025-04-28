package com.sample.salesforce.functions.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.salesforce.functions.config.SalesforceConfig;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.utility.ConfigurationUtility;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.stream.StreamSupport;

@Data
@Slf4j
@Service
public class ApiService {
    private final RestTemplate restTemplate;
    private final SalesforceConfig salesforceConfig;
    private final ObjectMapper objectMapper;
    private final String requestURL;

    public ApiService(RestTemplate restTemplate, ConfigurationUtility config) throws Exception {
        this.restTemplate = restTemplate;
        this.salesforceConfig = config.getSalesforceConfig();
        objectMapper = new ObjectMapper();
        requestURL = salesforceConfig.getDomainUrl() + "/services/data/v" + salesforceConfig.getVersion() + "/";
    }

    public String getAccessToken() throws JsonProcessingException {
        log.info("Requesting Salesforce Access Token.");

        String tokenUrl = salesforceConfig.getLoginUrl() + "/services/oauth2/token";

        //Set form parameters
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", salesforceConfig.getClientId());
        map.add("client_secret", salesforceConfig.getClientSecret());
        map.add("username", salesforceConfig.getUsername());
        map.add("password", salesforceConfig.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);
        JsonNode body = objectMapper.readTree(response.getBody());
        String token = body.path("access_token").asText();
        log.info("Request for Salesforce Access Token complete.");
        return token;
    }

    public String getQuery(String accessToken, String sObjectName, String query) throws JsonProcessingException, ApiResponseException {
        log.info("Requesting Salesforce Object: {}.", sObjectName);

        String url = requestURL + "query/?q=" + query;
        String responseBody = getRequest(accessToken, url);

        log.info("Salesforce Object: {} request complete.", sObjectName);
        return responseBody;
    }

    public String getDescribe(String accessToken, String sObjectName) throws ApiResponseException, JsonProcessingException {
        log.info("Requesting Salesforce Describe for: {}.", sObjectName);

        String url = requestURL + "sobjects/" + sObjectName + "/describe";
        String responseBody = getRequest(accessToken, url);

        log.info("Salesforce Describe for: {} request complete.", sObjectName);
        return responseBody;
    }

    public String postRequest(String accessToken, String sObjectName, String data) throws ApiResponseException, JsonProcessingException {
        log.info("Creating Salesforce Objects: {}.", sObjectName);
        String responseBody = operationRequest(accessToken, data, HttpMethod.POST, null, null);
        log.info("Salesforce Objects: {} creation complete.", sObjectName);
        return responseBody;
    }

    public String patchRequest(String accessToken, String sObjectName, String data) throws ApiResponseException, JsonProcessingException {
        log.info("Updating Salesforce Objects: {}.", sObjectName);
        String responseBody = operationRequest(accessToken, data, HttpMethod.PATCH, null, null);
        log.info("Salesforce Objects: {} update complete.", sObjectName);
        return responseBody;
    }

    public String upsertRequest(String accessToken, String sObjectName, String data, String externalField) throws ApiResponseException, JsonProcessingException {
        log.info("Upserting Salesforce Objects: {}.", sObjectName);
        String responseBody = operationRequest(accessToken, data, HttpMethod.PATCH, externalField, sObjectName);
        log.info("Salesforce Objects: {} upsert complete.", sObjectName);
        return responseBody;
    }

    private String getRequest(String accessToken, String url) throws JsonProcessingException, ApiResponseException {
        HttpHeaders headers = buildHeaders(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        //Send the GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return validateResponse(response);
    }

    private String operationRequest(String accessToken, String data, HttpMethod method, String externalField, String sObjectName) throws ApiResponseException, JsonProcessingException {
        String url = requestURL + "/composite/sobjects";

        if (externalField != null && !externalField.isEmpty()) {
            url += "/" + sObjectName + "/" + externalField;
        }

        HttpHeaders headers = buildHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(WrapDataForComposite(data), headers);

        // Send the Operation request
        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
        return validateResponse(response);
    }

    private HttpHeaders buildHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private String validateResponse(ResponseEntity<String> response) throws JsonProcessingException, ApiResponseException {
        HttpStatusCode statusCode = response.getStatusCode();
        String resp = response.getBody();

        if (!statusCode.is2xxSuccessful()) {
            String msg = String.format("Request failed with Status Code: %s. Error Message: %s", statusCode, resp);
            log.error(msg);
            throw new ApiResponseException(new ApiResponse<>(statusCode, resp));
        }

        // Salesforce will sometimes return a 200 OK but still have errors
        // Validate whether the response content contains the error(s) array
        JsonNode json = objectMapper.readTree(resp);
        if (json.isArray()) {
            boolean hasErrors = StreamSupport.stream(json.spliterator(), false)
                    .anyMatch(item -> {
                        boolean success = item.path("success").asBoolean(true);
                        JsonNode errors = item.path("errors");
                        return !success || (errors.isArray() && !errors.isEmpty());
                    });
            if (hasErrors) {
                log.error("Salesforce logical error: {}", resp);
                throw new ApiResponseException(new ApiResponse<>(HttpStatusCode.valueOf(400), resp));
            }
        }
        return resp;
    }

    private String WrapDataForComposite(String data) {
        return String.format("{\"allOrNone\": false, \"records\":  %s}", data);
    }
}