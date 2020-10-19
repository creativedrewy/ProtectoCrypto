package com.creativedrewy.protectocrypto.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import com.mkyong.crypto.encryptor.EncryptorAesGcmPassword
import javax.inject.Inject

class EncryptDecryptViewModel @Inject constructor(
    private val textEncryptionUseCase: TextEncryptDecryptUseCase
) : ViewModel() {

    private val encryptor: EncryptorAesGcmPassword = EncryptorAesGcmPassword()

    fun encodeData(key: String, data: String) {
        //val result = textEncryptionUseCase.encryptText(key, data)
        val result = encryptor.encrypt("world".toByteArray(Charsets.UTF_8), "hello")

        Log.v("Andrew", "Your result: $result")

        val decrypt = encryptor.decrypt(result, "hello")
        Log.v("Andrew", "decrypted: $decrypt")
    }

    fun decodeData(key: String, data: String) {

    }
}