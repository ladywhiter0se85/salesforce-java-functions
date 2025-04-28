package com.sample.salesforce.functions.utility;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionUtility {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";

    public EncryptionUtility() {
    }

    public String encrypt(String key, String iv, String plainText) throws Exception {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String key, String iv, String encryptedText) throws Exception {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(plainText);
    }

    private Cipher initCipher(int mode, String key, String iv) throws Exception {
        SecretKey secretKey = buildSecretKey(key);
        IvParameterSpec ivSpec = buildIV(iv);
        // Initialize the cipher in CBC mode with padding
        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        cipher.init(mode, secretKey, ivSpec);
        return cipher;
    }

    private SecretKey buildSecretKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    private IvParameterSpec buildIV(String iv) {
        byte[] decodedIv = Base64.getDecoder().decode(iv);
        return new IvParameterSpec(decodedIv);
    }
}
