package com.sample.salesforce.functions.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationResponse {
    private String id;
    private boolean success;
    private boolean created;
}
