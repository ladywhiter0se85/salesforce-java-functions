package com.sample.salesforce.functions.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.sample.salesforce.functions.exception.ApiResponseException;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.service.ApiService;
import com.sample.salesforce.functions.utility.functional.ThrowingSupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.*;

@Slf4j
@Component
public final class FunctionUtility {

    public static <T> ApiResponse<T> getSingleSObjectResponse(ApiService apiService, String sObjectName, String query, Class<T> clazz) {
        ApiResponse<List<T>> sObjectResponse = getSObjectResponse(apiService, sObjectName, query, clazz);
        return new ApiResponse<>(sObjectResponse.getData().get(0));
    }

    public static <T> ApiResponse<List<T>> getSObjectResponse(ApiService apiService, String sObjectName, String query, Class<T> clazz) {
        log.info("{} Function: Get Request Started.", sObjectName);
        return processErrors(() -> {
            String accessToken = apiService.getAccessToken();
            String initialResp = apiService.getQuery(accessToken, sObjectName, query);

            JsonNode json = apiService.getObjectMapper().readTree(initialResp);
            JsonNode records = json.path("records");

            // Check if "records" exists and is a non-empty array
            if (!records.isArray() || records.isEmpty()) {
                log.warn("{} Function: No records found.", sObjectName);
                throw new ApiResponseException(new ApiResponse<String>(HttpStatusCode.valueOf(404), "No records found."));
            }

            List<T> resp = apiService.getObjectMapper().readValue(records.toString(), apiService.getObjectMapper().getTypeFactory().constructCollectionType(List.class, clazz));
            log.info("{} Function: Get Request Complete.", sObjectName);
            return resp;
        });
    }

    public static ApiResponse<List<OperationResponse>> processSObjectOperation(ApiService apiService, String sObjectName, HttpMethod method, String body, String externalField) {
        log.info("{} Function: Request Started.", sObjectName);
        return processErrors(() -> {
            String accessToken = apiService.getAccessToken();

            String initialResp;
            if (method.equals(POST)) {
                initialResp = apiService.postRequest(accessToken, sObjectName, body);
            } else if (method.equals(PATCH)) {
                initialResp = apiService.patchRequest(accessToken, sObjectName, body);
            } else if (method.equals(PUT)) {
                initialResp = apiService.upsertRequest(accessToken, sObjectName, body, externalField);
            } else {
                throw new UnsupportedOperationException("HTTP method " + method + " is not supported.");
            }

            List<OperationResponse> resp = apiService.getObjectMapper().readValue(initialResp, new TypeReference<>() {
            });
            log.info("{} Function: Request Complete.", sObjectName);
            return resp;
        });
    }

    // This method uses the filter format (Field:Value1,Value2,Value3&Field2:Value1)
    // It splits the key value pairs by : and conditions which are separated by &
    // Building an SQL WHERE clas with IN for multiple values and = for single.
    public static String buildWhereFilter(String filter, boolean isAnd) {
        if (filter == null || filter.trim().isEmpty()) {
            return "";
        }

        List<String> conditions = Arrays.stream(filter.split("&"))
                .filter(pair -> pair.contains(":"))
                .map(pair -> pair.split(":", 2))
                .filter(kv -> kv.length == 2)
                .map(kv -> {
                    String column = kv[0].trim();
                    List<String> values = Arrays.stream(kv[1].split(","))
                            .map(String::trim)
                            .map(v -> "'" + v + "'")
                            .collect(Collectors.toList());

                    if (values.size() > 1) {
                        return column + " IN (" + String.join(", ", values) + ")";
                    } else {
                        return column + " = " + values.get(0);
                    }
                })
                .collect(Collectors.toList());

        String joinOperator = isAnd ? " AND " : " OR ";
        return conditions.isEmpty() ? "" : String.join(joinOperator, conditions);
    }

    private static <T> ApiResponse<T> processErrors(ThrowingSupplier<T> operation) {
        try {
            return new ApiResponse<>(operation.get());
        } catch (ApiResponseException e) {
            return new ApiResponse<>(e.apiResponse.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return new ApiResponse<>(HttpStatusCode.valueOf(500), e.getMessage());
        }
    }
}
