package com.mkyong.crypto.encryptor

import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Derived From mkyong.com:
 * https://mkyong.com/java/java-aes-encryption-and-decryption/
 * And:
 * https://github.com/mkyong/core-java
 *
 * MIT License: https://github.com/mkyong/core-java/blob/master/LICENSE
 */
class CryptoUtils {
    fun getRandomNonce(numBytes: Int): ByteArray {
        val nonce = ByteArray(numBytes)
        SecureRandom().nextBytes(nonce)
        return nonce
    }

    // AES 256 bits secret key derived from a password
    fun getAESKeyFromPassword(password: CharArray?, salt: ByteArray?): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")

        // iterationCount = 65536, keyLength = 256
        val spec: KeySpec = PBEKeySpec(password, salt, 65536, 256)
        return SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
    }
}