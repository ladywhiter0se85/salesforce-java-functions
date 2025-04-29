package com.sample.salesforce.functions.function.mocked;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.salesforce.functions.function.Product2Functions;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Product2;
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

public class Product2FunctionsMockedTest extends TestInitializer {
    @Mock
    private Product2Functions mockProduct2Functions;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getAllProduct2sTest() throws IOException {
        String json = ResponseUtility.readFile("mocked/product2/product2sResponse.json");
        List<Product2> product2s = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockProduct2Functions.getAllProduct2s()).thenReturn(new ApiResponse<>(product2s));

        ApiResponse<List<Product2>> resp = mockProduct2Functions.getAllProduct2s();
        assertNotNull(resp, "Response should not be null.");
        assertEquals("S001", resp.getData().get(0).getProductCode());
    }

    @Test
    public void getProduct2ByIdTest() throws IOException {
        String product2Id = "mockedProduct2IdOne";
        String json = ResponseUtility.readFile("mocked/product2/product2Response.json");
        Product2 product2 = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockProduct2Functions.getProduct2ById(product2Id)).thenReturn(new ApiResponse<>(product2));

        ApiResponse<Product2> resp = mockProduct2Functions.getProduct2ById(product2Id);
        assertNotNull(resp, "Response should not be null.");
        assertEquals("Mocked Software Subscription Product One", resp.getData().getName());
    }

    @Test
    public void getProduct2sByFilterOneToOneAndTest() throws IOException {
        String filter = "Type__c:Base Product&Family:Hardware";
        String json = ResponseUtility.readFile("mocked/product2/product2sResponse.json");
        List<Product2> product2s = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockProduct2Functions.getProduct2sByFilter(filter, true)).thenReturn(new ApiResponse<>(product2s));

        ApiResponse<List<Product2>> resp = mockProduct2Functions.getProduct2sByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void getProduct2sByFilterOneToManyAndTest() throws IOException {
        String filter = "IsActive:true";
        String json = ResponseUtility.readFile("mocked/product2/product2sResponse.json");
        List<Product2> product2s = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockProduct2Functions.getProduct2sByFilter(filter, true)).thenReturn(new ApiResponse<>(product2s));

        ApiResponse<List<Product2>> resp = mockProduct2Functions.getProduct2sByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void getProduct2sByFilterManyToOneOrTest() throws IOException {
        String filter = "ProductCode:S001,H001&Id:mockedProduct2IdTwo";
        String json = ResponseUtility.readFile("mocked/product2/product2sResponse.json");
        List<Product2> product2s = objectMapper.readValue(json, new TypeReference<>() {
        });

        when(mockProduct2Functions.getProduct2sByFilter(filter, false)).thenReturn(new ApiResponse<>(product2s));

        ApiResponse<List<Product2>> resp = mockProduct2Functions.getProduct2sByFilter(filter, false);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(2, resp.getData().size());
    }

    @Test
    public void postProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/postProduct2sRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockProduct2Functions.postProduct2s(body)).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockProduct2Functions.postProduct2s(body);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @Test
    public void patchProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/patchProduct2sRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockProduct2Functions.patchProduct2s(body)).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockProduct2Functions.patchProduct2s(body);
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @Test
    public void upsertProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/upsertProduct2sRequest.json");
        String opJson = ResponseUtility.readFile("mocked/operationResponse.json");
        List<OperationResponse> operationResp = objectMapper.readValue(opJson, new TypeReference<>() {
        });

        when(mockProduct2Functions.upsertProduct2s(body, "Id")).thenReturn(new ApiResponse<>(operationResp));

        ApiResponse<List<OperationResponse>> resp = mockProduct2Functions.upsertProduct2s(body, "Id");
        assertNotNull(resp, "Response should not be null.");
        assertEquals(1, resp.getData().size());
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        mockProduct2Functions = mock(Product2Functions.class);
    }
}