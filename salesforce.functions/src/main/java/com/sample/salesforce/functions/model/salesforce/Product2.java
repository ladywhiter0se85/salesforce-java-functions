package com.sample.salesforce.functions.model.salesforce;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Product2 {
    private Attributes attributes = new Attributes("Product2");
    @JsonAlias({"id", "Id"})
    private String id;
    @JsonAlias({"name", "Name"})
    private String name;
    @JsonAlias({"productCode", "ProductCode"})
    private String productCode;
    @JsonAlias({"description", "Description"})
    private String description;
    @JsonAlias({"isActive", "IsActive"})
    private boolean isActive;
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
    @JsonAlias({"family", "Family"})
    private String family;
    @JsonAlias({"externalDataSourceId", "ExternalDataSourceId"})
    public String externalDataSourceId;
    @JsonAlias({"externalId", "ExternalId"})
    private String externalId;
    @JsonAlias({"displayUrl", "DisplayUrl"})
    private String displayUrl;
    @JsonAlias({"quantityUnitOfMeasure", "QuantityUnitOfMeasure"})
    private String quantityUnitOfMeasure;
    @JsonAlias({"isDeleted", "IsDeleted"})
    private boolean isDeleted;
    @JsonAlias({"isArchived", "IsArchived"})
    private boolean isArchived;
    @JsonAlias({"lastViewedDate", "LastViewedDate"})
    private OffsetDateTime lastViewedDate;
    @JsonAlias({"lastReferencedDate", "LastReferencedDate"})
    private OffsetDateTime lastReferencedDate;
    @JsonAlias({"stockKeepingUnit", "StockKeepingUnit"})
    private String stockKeepingUnit;
    @JsonAlias({"type", "Type"})
    private String type;
    @JsonAlias({"productClass", "ProductClass"})
    private String productClass;
    @JsonAlias({"type__c", "Type__c"})
    private String type__c;
}
