package com.creativedrewy.protectocrypto.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import javax.inject.Inject

class EncryptDecryptViewModel @Inject constructor(
    private val textEncryptionUseCase: TextEncryptDecryptUseCase
) : ViewModel() {

    fun encodeData(key: String, data: String) {
        val result = textEncryptionUseCase.encryptText(key, data)

        Log.v("Andrew", "Your result: $result")
    }

    fun decodeData(key: String, data: String) {

    }
}