package com.sample.salesforce.functions.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "salesforce")
@Data
public class SalesforceConfig {

    private String loginUrl;
    private String domainUrl;
    private String version;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
    private String secretToken;
}
