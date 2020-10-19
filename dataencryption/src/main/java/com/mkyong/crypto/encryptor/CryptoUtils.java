package com.mkyong.crypto.encryptor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Derived From mkyong.com:
 * https://mkyong.com/java/java-aes-encryption-and-decryption/
 * And:
 * https://github.com/mkyong/core-java
 *
 * MIT License: https://github.com/mkyong/core-java/blob/master/LICENSE
 */
public class CryptoUtils {

    public byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);

        return nonce;
    }

    // AES 256 bits secret key derived from a password
    public SecretKey getAESKeyFromPassword(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // iterationCount = 65536, keyLength = 256
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }
}
