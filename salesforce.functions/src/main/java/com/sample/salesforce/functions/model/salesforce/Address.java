package com.sample.salesforce.functions.model.salesforce;

import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Address {
    private Attributes attributes = new Attributes("Address");
    @JsonAlias({"id", "Id"})
    private String id;
    @JsonAlias({"isDeleted", "IsDeleted"})
    private boolean isDeleted;
    @JsonAlias({"name", "Name"})
    private String name;
    @JsonAlias({"createdDate", "CreatedDate"})
    private OffsetDateTime createdDate;
    public Object createdById;
    @JsonAlias({"lastModifiedDate", "LastModifiedDate"})
    private OffsetDateTime lastModifiedDate;
    public Object lastModifiedById;
    @JsonAlias({"systemModstamp", "SystemModstamp"})
    private OffsetDateTime systemModstamp;
    public Object parentId;
    @JsonAlias({"locationType", "LocationType"})
    private String locationType;
    @JsonAlias({"addressType", "AddressType"})
    private String addressType;
    @JsonAlias({"street", "Street"})
    private String street;
    @JsonAlias({"city", "City"})
    private String city;
    @JsonAlias({"state", "State"})
    private String state;
    @JsonAlias({"postalCode", "PostalCode"})
    private String postalCode;
    @JsonAlias({"country", "Country"})
    private String country;
    @JsonAlias({"stateCode", "StateCode"})
    private String stateCode;
    @JsonAlias({"countryCode", "CountryCode"})
    private String countryCode;
    @JsonAlias({"latitude", "Latitude"})
    private double latitude;
    @JsonAlias({"longitude", "Longitude"})
    private double longitude;
    @JsonAlias({"geocodeAccuracy", "GeocodeAccuracy"})
    private String geocodeAccuracy;
    @JsonAlias({"address", "Address"})
    private Address address;
    @JsonAlias({"description", "Description"})
    private String description;
    @JsonAlias({"drivingDirections", "DrivingDirections"})
    private String drivingDirections;
    @JsonAlias({"timeZone", "TimeZone"})
    private String timeZone;
}
