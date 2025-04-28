package com.sample.salesforce.functions.utility;

import com.sample.salesforce.functions.config.EncryptionConfig;
import com.sample.salesforce.functions.config.SalesforceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationUtility {
    private final SalesforceConfig salesforceConfig;
    private final EncryptionConfig encryptionConfig;
    private static final String ENV = System.getProperty("app.env", "dev");  // default to dev if not set

    @Autowired
    public ConfigurationUtility(SalesforceConfig salesforceConfig, EncryptionConfig encryptionConfig) {
        this.salesforceConfig = salesforceConfig;
        this.encryptionConfig = encryptionConfig;
    }

    public SalesforceConfig getSalesforceConfig() throws Exception {
        if ("test".equals(ENV) || "dev".equals(ENV)) {
            // Decrypt Salesforce secure variables for dev/test environments
            return decryptSalesforceConfig();
        } else {
            return salesforceConfig; // No decryption for prd
        }
    }

    private SalesforceConfig decryptSalesforceConfig() throws Exception {
        String key = encryptionConfig.getKey();
        String iv = encryptionConfig.getIv();

        // Decryption Sensitive Data
        EncryptionUtility encryptionUtility = new EncryptionUtility();
        salesforceConfig.setClientId(encryptionUtility.decrypt(key, iv, salesforceConfig.getClientId()));
        salesforceConfig.setClientSecret(encryptionUtility.decrypt(key, iv, salesforceConfig.getClientSecret()));
        salesforceConfig.setUsername(encryptionUtility.decrypt(key, iv, salesforceConfig.getUsername()));
        salesforceConfig.setPassword(encryptionUtility.decrypt(key, iv, salesforceConfig.getPassword()));
        salesforceConfig.setSecretToken(encryptionUtility.decrypt(key, iv, salesforceConfig.getSecretToken()));
        return salesforceConfig;
    }

}
