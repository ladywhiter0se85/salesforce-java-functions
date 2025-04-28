package com.sample.salesforce.functions.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "encryption")
@Data
public class EncryptionConfig {
    private String key;
    private String iv;
}