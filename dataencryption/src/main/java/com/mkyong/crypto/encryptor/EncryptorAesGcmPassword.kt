package com.mkyong.crypto.encryptor

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

/**
 * Derived from mkyong.com:
 * https://mkyong.com/java/java-aes-encryption-and-decryption/
 * and
 * https://github.com/mkyong/core-java
 *
 * MIT License: https://github.com/mkyong/core-java/blob/master/LICENSE
 */
class EncryptorAesGcmPassword {
    companion object {
        private const val ENCRYPT_ALGO = "AES/GCM/NoPadding"
        private const val TAG_LENGTH_BIT = 128 // must be one of {128, 120, 112, 104, 96}
        private const val IV_LENGTH_BYTE = 12
        private const val SALT_LENGTH_BYTE = 16

        private val UTF_8 = StandardCharsets.UTF_8
    }

    private val cryptoUtils = CryptoUtils()

    // return a base64 encoded AES encrypted text
    @Throws(Exception::class)
    fun encrypt(pText: ByteArray?, password: String): String {

        // 16 bytes salt
        val salt = cryptoUtils.getRandomNonce(SALT_LENGTH_BYTE)

        // GCM recommended 12 bytes iv?
        val iv = cryptoUtils.getRandomNonce(IV_LENGTH_BYTE)

        // secret key from password
        val aesKeyFromPassword = cryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt)
        val cipher = Cipher.getInstance(ENCRYPT_ALGO)

        // ASE-GCM needs GCMParameterSpec
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, GCMParameterSpec(TAG_LENGTH_BIT, iv))
        val cipherText = cipher.doFinal(pText)

        // prefix IV and Salt to cipher text
        val cipherTextWithIvSalt = ByteBuffer.allocate(iv.size + salt.size + cipherText.size)
                .put(iv)
                .put(salt)
                .put(cipherText)
                .array()

        // string representation, base64, send this string to other for decryption.
        return Base64.getEncoder().encodeToString(cipherTextWithIvSalt)
    }

    // we need the same password, salt and iv to decrypt it
    @Throws(Exception::class)
    fun decrypt(cText: String, password: String): String {
        val decode = Base64.getDecoder().decode(cText.toByteArray(UTF_8))

        // get back the iv and salt from the cipher text
        val bb = ByteBuffer.wrap(decode)
        val iv = ByteArray(IV_LENGTH_BYTE)
        bb[iv]
        val salt = ByteArray(SALT_LENGTH_BYTE)
        bb[salt]
        val cipherText = ByteArray(bb.remaining())
        bb[cipherText]

        // get back the aes key from the same password and salt
        val aesKeyFromPassword = cryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt)
        val cipher = Cipher.getInstance(ENCRYPT_ALGO)
        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, GCMParameterSpec(TAG_LENGTH_BIT, iv))
        val plainText = cipher.doFinal(cipherText)
        return String(plainText, UTF_8)
    }
}