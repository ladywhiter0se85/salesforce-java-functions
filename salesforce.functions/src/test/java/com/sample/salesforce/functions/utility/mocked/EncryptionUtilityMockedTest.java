package com.sample.salesforce.functions.utility.mocked;

import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.utility.EncryptionUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class EncryptionUtilityMockedTest extends TestInitializer {
    @Mock
    private EncryptionUtility mockEncryptionUtility;
    private final String key = "mockedKey";
    private final String iv = "mockedIv";

    @Test
    public void encryptTest() throws Exception {
        String plainString = "testClientId";
        String encryptedTextResp = "zhZAOZ40nAFZrI3kUEjH0w==";

        when(mockEncryptionUtility.encrypt(key, iv, plainString)).thenReturn(encryptedTextResp);

        String encryptedText = mockEncryptionUtility.encrypt(key, iv, plainString);
        assertNotNull(encryptedText);
        assertEquals(encryptedTextResp, encryptedText);
    }

    @Test
    public void decryptTest() throws Exception {
        String plainString = "testClientId";
        String encryptedText = "zhZAOZ40nAFZrI3kUEjH0w==";

        when(mockEncryptionUtility.decrypt(key, iv, encryptedText)).thenReturn(plainString);

        String decryptedText = mockEncryptionUtility.decrypt(key, iv, encryptedText);
        assertNotNull(decryptedText);
        assertEquals(plainString, decryptedText);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
}