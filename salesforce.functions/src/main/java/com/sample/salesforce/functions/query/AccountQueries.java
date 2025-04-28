package com.sample.salesforce.functions.query;

import com.sample.salesforce.functions.utility.FunctionUtility;
import lombok.Data;

@Data
public class AccountQueries {
    private static final String SHARED_FIELDS = "SELECT Id, Name, Active__c, BillingAddress, Description, Phone, Type FROM Account";
    private static final String GET_ALL_ACCOUNTS_QUERY = SHARED_FIELDS;
    private static final String GET_ACCOUNT_BY_ID_QUERY = SHARED_FIELDS + " WHERE Id = '?'";
    private static final String GET_ACCOUNTS_BY_FILTER_QUERY = SHARED_FIELDS + " WHERE ";

    public static String getAllAccountsQuery() {
        return GET_ALL_ACCOUNTS_QUERY;
    }

    public static String getAccountsByFilter(String filter, boolean isAnd) {
        return GET_ACCOUNTS_BY_FILTER_QUERY + FunctionUtility.buildWhereFilter(filter, isAnd);
    }

    public static String getAccountById(String accountId) {
        return GET_ACCOUNT_BY_ID_QUERY.replace("?", accountId);
    }
}
