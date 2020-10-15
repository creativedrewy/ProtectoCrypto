package com.creativedrewy.dataencryption

import java.security.spec.AlgorithmParameterSpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class TextEncryptDecryptUseCase {

    fun encryptText(key: String, algorithm: String, value: String) {

        val bytes = key.toByteArray(Charsets.UTF_8)

        val secretKey: SecretKey = SecretKeySpec(Base64.getDecoder().decode(bytes), "AES")
        val iv: AlgorithmParameterSpec = IvParameterSpec(Base64.getDecoder().decode(bytes))

        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

        //String(Base64.encode(cipher.doFinal(value.getBytes("UTF-8")), Base64.NO_WRAP));
    }

}