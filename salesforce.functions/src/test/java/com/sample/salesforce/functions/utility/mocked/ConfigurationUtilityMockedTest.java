package com.sample.salesforce.functions.utility.mocked;

import com.sample.salesforce.functions.config.SalesforceConfig;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.utility.ConfigurationUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ConfigurationUtilityMockedTest extends TestInitializer {
    @Mock
    private SalesforceConfig mockSalesforceConfig;
    @Mock
    private ConfigurationUtility configurationUtility;

    @Test
    public void getSalesforceConfigTest() throws Exception {
        when(mockSalesforceConfig.getUsername()).thenReturn("mockedUsername");
        when(mockSalesforceConfig.getClientId()).thenReturn("mockedClientId");

        when(configurationUtility.getSalesforceConfig()).thenReturn(mockSalesforceConfig);

        SalesforceConfig salesforceConfig = configurationUtility.getSalesforceConfig();
        assertNotNull(salesforceConfig);
        assertEquals(mockSalesforceConfig.getClientId(), salesforceConfig.getClientId());
        assertEquals(mockSalesforceConfig.getUsername(), salesforceConfig.getUsername());
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
}