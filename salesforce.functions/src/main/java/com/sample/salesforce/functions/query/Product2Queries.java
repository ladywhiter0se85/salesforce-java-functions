package com.sample.salesforce.functions.query;

import com.sample.salesforce.functions.utility.FunctionUtility;
import lombok.Data;

@Data
public class Product2Queries {
    private static final String SHARED_FIELDS = "SELECT Id, Name, ProductCode, Description, IsActive, Family, Type__c FROM Product2";
    private static final String GET_ALL_PRODUCT2S_QUERY = SHARED_FIELDS;
    private static final String GET_PRODUCT2_BY_ID_QUERY = SHARED_FIELDS + " WHERE Id = '?'";
    private static final String GET_PRODUCT2S_BY_FILTER_QUERY = SHARED_FIELDS + " WHERE ";

    public static String getAllProduct2sQuery() {
        return GET_ALL_PRODUCT2S_QUERY;
    }

    public static String getProduct2sByFilter(String filter, boolean isAnd) {
        return GET_PRODUCT2S_BY_FILTER_QUERY + FunctionUtility.buildWhereFilter(filter, isAnd);
    }

    public static String getProduct2ById(String product2Id) {
        return GET_PRODUCT2_BY_ID_QUERY.replace("?", product2Id);
    }
}
