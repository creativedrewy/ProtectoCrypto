package com.creativedrewy.protectocrypto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import com.creativedrewy.protectocrypto.usecase.IncomingDataUseCase
import javax.inject.Inject

class EncryptDecryptViewModelFactory @Inject constructor(
    private val textEncryptDecryptUseCase: TextEncryptDecryptUseCase,
    private val incomingDataUseCase: IncomingDataUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EncryptDecryptViewModel::class.java)) {
            EncryptDecryptViewModel(
                textEncryptDecryptUseCase,
                incomingDataUseCase
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}