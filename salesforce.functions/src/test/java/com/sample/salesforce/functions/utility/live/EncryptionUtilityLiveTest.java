package com.sample.salesforce.functions.utility.live;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.salesforce.functions.config.SalesforceConfig;
import com.sample.salesforce.functions.initializer.TestInitializer;
import com.sample.salesforce.functions.utility.EncryptionUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EncryptionUtilityLiveTest extends TestInitializer {
    @Autowired
    private EncryptionUtility encryptionUtility;
    private final String key = "/Iam1eJnM0uEBqeRzZngRPd2HtIe/pTo+lj9Q3j2RtQ=";
    private final String iv = "wFIjU2sGvgE6R6Y3uH1Pcg==";

    @Test
    public void encryptTest() throws Exception {
        String plainString = "testClientId";
        String encryptedText = encryptionUtility.encrypt(key, iv, plainString);
        assertNotNull(encryptedText);
    }

    @Test
    public void decryptTest() throws Exception {
        String plainString = "testClientId";
        String encryptedText = "zhZAOZ40nAFZrI3kUEjH0w==";
        String decryptedText = encryptionUtility.decrypt(key, iv, encryptedText);
        assertNotNull(decryptedText);
        assertEquals(plainString, decryptedText);
    }

    @Test
    public void generateEncryptionKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey key = keyGen.generateKey();

        byte[] iv = new byte[16]; // 16 bytes for AES
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        //Convert to Base64
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        String base64IV = Base64.getEncoder().encodeToString(iv);

        System.out.println("Key: " + base64Key);
        System.out.println("IV:" + base64IV);
    }

    @Test
    public void encryptProperties() throws Exception {
        SalesforceConfig salesforceConfig = new SalesforceConfig();
        salesforceConfig.setClientId(encryptionUtility.encrypt(key, iv, "thisIsNotGoingToBeARealClientIdJustMockedForRepos"));
        salesforceConfig.setClientSecret(encryptionUtility.encrypt(key, iv, "thisIsNotGoingToBeARealClientSecretJustMockedForRepo"));
        salesforceConfig.setUsername(encryptionUtility.encrypt(key, iv, "mockedSalesforceAccount@mock.com"));
        salesforceConfig.setPassword(encryptionUtility.encrypt(key, iv, "mockedSalesforceAccountPassword"));
        salesforceConfig.setSecretToken(encryptionUtility.encrypt(key, iv, "mockedSalesforceAccountSecretToken"));

        ObjectMapper objectMapper = new ObjectMapper();
        String encryptedJson = objectMapper.writeValueAsString(salesforceConfig);
        assertNotNull(encryptedJson);
    }
}