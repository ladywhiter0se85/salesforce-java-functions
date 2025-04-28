package com.sample.salesforce.functions.model.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Attributes {
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;

    public Attributes() {
    }

    public Attributes(String type) {
        this.type = type;
    }
}
