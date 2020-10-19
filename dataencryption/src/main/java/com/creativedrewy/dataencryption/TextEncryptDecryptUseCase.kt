package com.creativedrewy.dataencryption

import com.mkyong.crypto.encryptor.CryptoUtils
import java.nio.ByteBuffer
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

/**
 * Encryption/decryption methods derived from mkyong.com:
 * https://mkyong.com/java/java-aes-encryption-and-decryption/
 * and
 * https://github.com/mkyong/core-java
 *
 * MIT License: https://github.com/mkyong/core-java/blob/master/LICENSE
 */
class TextEncryptDecryptUseCase @Inject constructor(
    val cryptoUtils: CryptoUtils
) {

    fun encryptText(key: String, data: String): String {
        val salt = cryptoUtils.getRandomNonce(CryptoUtils.SALT_LENGTH_BYTE)
        val iv = cryptoUtils.getRandomNonce(CryptoUtils.IV_LENGTH_BYTE) // GCM recommended 12 bytes iv?

        // secret key from password
        val aesKeyFromPassword = cryptoUtils.getAESKeyFromPassword(key.toCharArray(), salt)
        val cipher = Cipher.getInstance(CryptoUtils.ENCRYPT_ALGO)

        // ASE-GCM needs GCMParameterSpec
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, GCMParameterSpec(CryptoUtils.TAG_LENGTH_BIT, iv))
        val cipherText = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        // prefix IV and Salt to cipher text
        val cipherTextWithIvSalt = ByteBuffer.allocate(iv.size + salt.size + cipherText.size)
                .put(iv)
                .put(salt)
                .put(cipherText)
                .array()

        return Base64.getEncoder().encodeToString(cipherTextWithIvSalt)
    }

    fun decryptText(key: String, data: String): String {
        val decode = Base64.getDecoder().decode(data.toByteArray(Charsets.UTF_8))

        // get back the iv and salt from the cipher text
        val bb = ByteBuffer.wrap(decode)
        val iv = ByteArray(CryptoUtils.IV_LENGTH_BYTE)
        bb[iv]

        val salt = ByteArray(CryptoUtils.SALT_LENGTH_BYTE)
        bb[salt]

        val cipherText = ByteArray(bb.remaining())
        bb[cipherText]

        // get back the aes key from the same password and salt
        val aesKeyFromPassword = cryptoUtils.getAESKeyFromPassword(key.toCharArray(), salt)
        val cipher = Cipher.getInstance(CryptoUtils.ENCRYPT_ALGO)

        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, GCMParameterSpec(CryptoUtils.TAG_LENGTH_BIT, iv))
        val plainText = cipher.doFinal(cipherText)

        return String(plainText, Charsets.UTF_8)
    }
}