package com.creativedrewy.dataencryption

import java.security.MessageDigest
import java.security.spec.AlgorithmParameterSpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class TextEncryptDecryptUseCase @Inject constructor() {

    fun encryptText(key: String, value: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")

        val keyBytes = key.toByteArray(Charsets.UTF_8)
        val decodedKeyBytes = Base64.getDecoder().decode(keyBytes)

        val shaBytes = messageDigest.digest(decodedKeyBytes)

        val secretKey: SecretKey = SecretKeySpec(shaBytes, "AES")
        val iv: AlgorithmParameterSpec = IvParameterSpec(shaBytes)

        val cipher: Cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

        return Base64.getEncoder().encodeToString(cipher.doFinal(value.toByteArray(Charsets.UTF_8)))
    }

}