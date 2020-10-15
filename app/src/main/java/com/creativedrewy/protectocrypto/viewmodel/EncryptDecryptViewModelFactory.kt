package com.creativedrewy.protectocrypto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class EncryptDecryptViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EncryptDecryptViewModel::class.java)) {
            EncryptDecryptViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}