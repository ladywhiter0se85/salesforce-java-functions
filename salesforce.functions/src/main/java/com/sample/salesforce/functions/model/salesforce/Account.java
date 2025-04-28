package com.sample.salesforce.functions.model.salesforce;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Account {
    private Attributes attributes = new Attributes("Account");
    @JsonAlias({"id", "Id"})
    private String id;
    @JsonAlias({"isDeleted", "IsDeleted"})
    private boolean isDeleted;
    public Account masterRecordId;
    @JsonAlias({"name", "Name"})
    private String name;
    @JsonAlias({"type", "Type"})
    private String type;
    public Account parentId;
    @JsonAlias({"billingStreet", "BillingStreet"})
    private String billingStreet;
    @JsonAlias({"billingCity", "BillingCity"})
    private String billingCity;
    @JsonAlias({"billingState", "BillingState"})
    private String billingState;
    @JsonAlias({"billingPostalCode", "BillingPostalCode"})
    private String billingPostalCode;
    @JsonAlias({"billingCountry", "BillingCountry"})
    private String billingCountry;
    @JsonAlias({"billingStateCode", "BillingStateCode"})
    private String billingStateCode;
    @JsonAlias({"billingCountryCode", "BillingCountryCode"})
    private String billingCountryCode;
    @JsonAlias({"billingLatitude", "BillingLatitude"})
    private double billingLatitude;
    @JsonAlias({"billingLongitude", "BillingLongitude"})
    private double billingLongitude;
    @JsonAlias({"billingGeocodeAccuracy", "BillingGeocodeAccuracy"})
    private String billingGeocodeAccuracy;
    @JsonAlias({"billingAddress", "BillingAddress"})
    private Address billingAddress;
    @JsonAlias({"shippingStreet", "ShippingStreet"})
    private String shippingStreet;
    @JsonAlias({"shippingCity", "ShippingCity"})
    private String shippingCity;
    @JsonAlias({"shippingState", "ShippingState"})
    private String shippingState;
    @JsonAlias({"shippingPostalCode", "ShippingPostalCode"})
    private String shippingPostalCode;
    @JsonAlias({"shippingCountry", "ShippingCountry"})
    private String shippingCountry;
    @JsonAlias({"shippingStateCode", "ShippingStateCode"})
    private String shippingStateCode;
    @JsonAlias({"shippingCountryCode", "ShippingCountryCode"})
    private String shippingCountryCode;
    @JsonAlias({"shippingLatitude", "ShippingLatitude"})
    private double shippingLatitude;
    @JsonAlias({"shippingLongitude", "ShippingLongitude"})
    private double shippingLongitude;
    @JsonAlias({"shippingGeocodeAccuracy", "ShippingGeocodeAccuracy"})
    private String shippingGeocodeAccuracy;
    @JsonAlias({"shippingAddress", "ShippingAddress"})
    private Address shippingAddress;
    @JsonAlias({"phone", "Phone"})
    private String phone;
    @JsonAlias({"fax", "Fax"})
    private String fax;
    @JsonAlias({"accountNumber", "AccountNumber"})
    private String accountNumber;
    @JsonAlias({"website", "Website"})
    private String website;
    @JsonAlias({"photoUrl", "PhotoUrl"})
    private String photoUrl;
    @JsonAlias({"sic", "Sic"})
    private String sic;
    @JsonAlias({"industry", "Industry"})
    private String industry;
    @JsonAlias({"annualRevenue", "AnnualRevenue"})
    private double annualRevenue;
    @JsonAlias({"numberOfEmployees", "NumberOfEmployees"})
    private int numberOfEmployees;
    @JsonAlias({"ownership", "Ownership"})
    private String ownership;
    @JsonAlias({"tickerSymbol", "TickerSymbol"})
    private String tickerSymbol;
    @JsonAlias({"description", "Description"})
    private String description;
    @JsonAlias({"rating", "Rating"})
    private String rating;
    @JsonAlias({"site", "Site"})
    private String site;
    public Object ownerId;
    @JsonAlias({"createdDate", "CreatedDate"})
    private OffsetDateTime createdDate;
    public Object createdById;
    @JsonAlias({"lastModifiedDate", "LastModifiedDate"})
    private OffsetDateTime lastModifiedDate;
    public Object lastModifiedById;
    @JsonAlias({"systemModstamp", "SystemModstamp"})
    private OffsetDateTime systemModstamp;
    @JsonAlias({"lastActivityDate", "LastActivityDate"})
    private LocalDate lastActivityDate;
    @JsonAlias({"lastViewedDate", "LastViewedDate"})
    private OffsetDateTime lastViewedDate;
    @JsonAlias({"lastReferencedDate", "LastReferencedDate"})
    private OffsetDateTime lastReferencedDate;
    @JsonAlias({"jigsaw", "Jigsaw"})
    private String jigsaw;
    @JsonAlias({"jigsawCompanyId", "JigsawCompanyId"})
    private String jigsawCompanyId;
    @JsonAlias({"cleanStatus", "CleanStatus"})
    private String cleanStatus;
    @JsonAlias({"accountSource", "AccountSource"})
    private String accountSource;
    @JsonAlias({"dunsNumber", "DunsNumber"})
    private String dunsNumber;
    @JsonAlias({"tradestyle", "Tradestyle"})
    private String tradestyle;
    @JsonAlias({"naicsCode", "NaicsCode"})
    private String naicsCode;
    @JsonAlias({"naicsDesc", "NaicsDesc"})
    private String naicsDesc;
    @JsonAlias({"yearStarted", "YearStarted"})
    private String yearStarted;
    @JsonAlias({"sicDesc", "SicDesc"})
    private String sicDesc;
    public Object dandbCompanyId;
    public Object operatingHoursId;
    @JsonAlias({"customerPriority__c", "CustomerPriority__c"})
    private String customerPriority__c;
    @JsonAlias({"sLA__c", "SLA__c"})
    private String sLA__c;
    @JsonAlias({"active__c", "Active__c"})
    private String active__c;
    @JsonAlias({"numberofLocations__c", "NumberofLocations__c"})
    private double numberofLocations__c;
    @JsonAlias({"upsellOpportunity__c", "UpsellOpportunity__c"})
    private String upsellOpportunity__c;
    @JsonAlias({"sLASerialNumber__c", "SLASerialNumber__c"})
    private String sLASerialNumber__c;
    @JsonAlias({"sLAExpirationDate__c", "SLAExpirationDate__c"})
    private LocalDate sLAExpirationDate__c;
}
