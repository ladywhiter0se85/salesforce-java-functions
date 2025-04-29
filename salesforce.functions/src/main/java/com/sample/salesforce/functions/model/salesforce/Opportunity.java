package com.sample.salesforce.functions.model.salesforce;

import java.time.OffsetDateTime;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Opportunity {
    private Attributes attributes = new Attributes("Opportunity");
    @JsonAlias({"id", "Id"})
    private String id;
    @JsonAlias({"isDeleted", "IsDeleted"})
    private boolean isDeleted;
    @JsonAlias({"accountId", "AccountId"})
    public String accountId;
    @JsonAlias({"isPrivate", "IsPrivate"})
    private boolean isPrivate;
    @JsonAlias({"name", "Name"})
    private String name;
    @JsonAlias({"description", "Description"})
    private String description;
    @JsonAlias({"stageName", "StageName"})
    private String stageName;
    @JsonAlias({"amount", "Amount"})
    private double amount;
    @JsonAlias({"probability", "Probability"})
    private double probability;
    @JsonAlias({"expectedRevenue", "ExpectedRevenue"})
    private double expectedRevenue;
    @JsonAlias({"totalOpportunityQuantity", "TotalOpportunityQuantity"})
    private double totalOpportunityQuantity;
    @JsonAlias({"closeDate", "CloseDate"})
    private LocalDate closeDate;
    @JsonAlias({"type", "Type"})
    private String type;
    @JsonAlias({"nextStep", "NextStep"})
    private String nextStep;
    @JsonAlias({"leadSource", "LeadSource"})
    private String leadSource;
    @JsonAlias({"isClosed", "IsClosed"})
    private boolean isClosed;
    @JsonAlias({"isWon", "IsWon"})
    private boolean isWon;
    @JsonAlias({"forecastCategory", "ForecastCategory"})
    private String forecastCategory;
    @JsonAlias({"forecastCategoryName", "ForecastCategoryName"})
    private String forecastCategoryName;
    @JsonAlias({"campaignId", "CampaignId"})
    public String campaignId;
    @JsonAlias({"hasOpportunityLineItem", "HasOpportunityLineItem"})
    private boolean hasOpportunityLineItem;
    @JsonAlias({"pricebook2Id", "Pricebook2Id"})
    public String pricebook2Id;
    @JsonAlias({"ownerId", "OwnerId"})
    public String ownerId;
    @JsonAlias({"createdDate", "CreatedDate"})
    private OffsetDateTime createdDate;
    @JsonAlias({"createdById", "CreatedById"})
    public String createdById;
    @JsonAlias({"lastModifiedDate", "LastModifiedDate"})
    private OffsetDateTime lastModifiedDate;
    @JsonAlias({"lastModifiedById", "LastModifiedById"})
    public String lastModifiedById;
    @JsonAlias({"systemModstamp", "SystemModstamp"})
    private OffsetDateTime systemModstamp;
    @JsonAlias({"lastActivityDate", "LastActivityDate"})
    private LocalDate lastActivityDate;
    @JsonAlias({"pushCount", "PushCount"})
    private int pushCount;
    @JsonAlias({"lastStageChangeDate", "LastStageChangeDate"})
    private OffsetDateTime lastStageChangeDate;
    @JsonAlias({"fiscalQuarter", "FiscalQuarter"})
    private int fiscalQuarter;
    @JsonAlias({"fiscalYear", "FiscalYear"})
    private int fiscalYear;
    @JsonAlias({"fiscal", "Fiscal"})
    private String fiscal;
    @JsonAlias({"contactId", "ContactId"})
    public String contactId;
    @JsonAlias({"lastViewedDate", "LastViewedDate"})
    private OffsetDateTime lastViewedDate;
    @JsonAlias({"lastReferencedDate", "LastReferencedDate"})
    private OffsetDateTime lastReferencedDate;
    @JsonAlias({"hasOpenActivity", "HasOpenActivity"})
    private boolean hasOpenActivity;
    @JsonAlias({"hasOverdueTask", "HasOverdueTask"})
    private boolean hasOverdueTask;
    @JsonAlias({"lastAmountChangedHistoryId", "LastAmountChangedHistoryId"})
    public String lastAmountChangedHistoryId;
    @JsonAlias({"lastCloseDateChangedHistoryId", "LastCloseDateChangedHistoryId"})
    public String lastCloseDateChangedHistoryId;
    @JsonAlias({"deliveryInstallationStatus__c", "DeliveryInstallationStatus__c"})
    private String deliveryInstallationStatus__c;
    @JsonAlias({"trackingNumber__c", "TrackingNumber__c"})
    private String trackingNumber__c;
    @JsonAlias({"orderNumber__c", "OrderNumber__c"})
    private String orderNumber__c;
    @JsonAlias({"currentGenerators__c", "CurrentGenerators__c"})
    private String currentGenerators__c;
    @JsonAlias({"mainCompetitors__c", "MainCompetitors__c"})
    private String mainCompetitors__c;
}
