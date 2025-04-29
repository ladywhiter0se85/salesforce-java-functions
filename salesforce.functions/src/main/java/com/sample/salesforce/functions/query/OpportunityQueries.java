package com.sample.salesforce.functions.query;

import com.sample.salesforce.functions.utility.FunctionUtility;
import lombok.Data;

@Data
public class OpportunityQueries {
    private static final String SHARED_FIELDS = "SELECT Id, Name, AccountId, Amount, CloseDate, Description, TotalOpportunityQuantity, StageName, Type FROM Opportunity";
    private static final String GET_ALL_OPPORTUNITIES_QUERY = SHARED_FIELDS;
    private static final String GET_OPPORTUNITY_BY_ID_QUERY = SHARED_FIELDS + " WHERE Id = '?'";
    private static final String GET_OPPORTUNITIES_BY_FILTER_QUERY = SHARED_FIELDS + " WHERE ";

    public static String getAllOpportunitiesQuery() {
        return GET_ALL_OPPORTUNITIES_QUERY;
    }

    public static String getOpportunitiesByFilter(String filter, boolean isAnd) {
        return GET_OPPORTUNITIES_BY_FILTER_QUERY + FunctionUtility.buildWhereFilter(filter, isAnd);
    }

    public static String getOpportunityById(String opportunityId) {
        return GET_OPPORTUNITY_BY_ID_QUERY.replace("?", opportunityId);
    }
}
