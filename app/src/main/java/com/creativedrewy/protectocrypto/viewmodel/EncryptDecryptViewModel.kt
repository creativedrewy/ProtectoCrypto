package com.creativedrewy.protectocrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EncryptDecryptViewModel @Inject constructor(
    private val textEncryptionUseCase: TextEncryptDecryptUseCase
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> = MutableLiveData()

    fun encodeData(key: String, data: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                textEncryptionUseCase.encryptText(key, data)
            }

            viewState.postValue(ViewState(result))
        }
    }

    fun decodeData(key: String, data: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                textEncryptionUseCase.decryptText(key, data)
            }

            viewState.postValue(ViewState(result))
        }
    }
}