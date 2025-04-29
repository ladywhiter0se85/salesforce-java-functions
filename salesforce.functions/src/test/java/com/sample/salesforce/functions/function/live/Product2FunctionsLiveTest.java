package com.sample.salesforce.functions.function.live;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sample.salesforce.functions.function.Product2Functions;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.model.response.ApiResponse;
import com.sample.salesforce.functions.model.response.OperationResponse;
import com.sample.salesforce.functions.model.salesforce.Product2;
import com.sample.salesforce.functions.utility.ResponseUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Product2FunctionsLiveTest extends TestInitializer {
    @Autowired
    private Product2Functions product2Functions;

    @Test
    public void getAllProduct2sTest() {
        ApiResponse<List<Product2>> resp = product2Functions.getAllProduct2s();
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getProduct2ByIdTest() {
        ApiResponse<Product2> resp = product2Functions.getProduct2ById("01tgK000001h5ALQAY");
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getProduct2sByFilterOneToOneAndTest() {
        String filter = "ProductCode:003&Type__c:Digital";
        ApiResponse<List<Product2>> resp = product2Functions.getProduct2sByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getProduct2sByFilterOneToManyAndTest() {
        String filter = "Family:Maintenance,Software";
        ApiResponse<List<Product2>> resp = product2Functions.getProduct2sByFilter(filter, true);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void getProduct2sByFilterManyToOneOrTest() {
        String filter = "Type__c:Subscription,Service&ProductCode:006";
        ApiResponse<List<Product2>> resp = product2Functions.getProduct2sByFilter(filter, false);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void postProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/postProduct2sRequest.json");
        ApiResponse<List<OperationResponse>> resp = product2Functions.postProduct2s(body);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void patchProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/patchProduct2sRequest.json");
        ApiResponse<List<OperationResponse>> resp = product2Functions.patchProduct2s(body);
        assertNotNull(resp, "Response should not be null.");
    }

    @Test
    public void upsertProduct2sTest() throws IOException {
        String body = ResponseUtility.readFile("live/product2/upsertProduct2sRequest.json");
        ApiResponse<List<OperationResponse>> resp = product2Functions.upsertProduct2s(body, "Id");
        assertNotNull(resp, "Response should not be null.");
    }

    @BeforeEach
    public void init() throws JsonProcessingException {
        //Verify Product2Function
        assertNotNull(product2Functions, "Product2Function should be initialized.");
    }
}