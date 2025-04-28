package com.sample.salesforce.functions.utility.live;

import com.sample.salesforce.functions.config.SalesforceConfig;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.utility.ConfigurationUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigurationUtilityLiveTest extends TestInitializer {
    @Autowired
    private ConfigurationUtility configurationUtility;

    @Test
    public void getSalesforceConfigTest() throws Exception {
        SalesforceConfig salesforceConfig = configurationUtility.getSalesforceConfig();
        assertNotNull(salesforceConfig);
    }

    @BeforeEach
    public void init() {
        //Verify ConfigurationUtility
        assertNotNull(configurationUtility);
    }
}