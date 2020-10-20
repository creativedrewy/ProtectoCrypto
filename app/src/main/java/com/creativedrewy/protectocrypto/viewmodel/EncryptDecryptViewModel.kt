package com.creativedrewy.protectocrypto.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import com.creativedrewy.protectocrypto.usecase.Data
import com.creativedrewy.protectocrypto.usecase.IncomingDataUseCase
import com.creativedrewy.protectocrypto.usecase.Key
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EncryptDecryptViewModel @Inject constructor(
    private val textEncryptionUseCase: TextEncryptDecryptUseCase,
    private val incomingDataUseCase: IncomingDataUseCase
) : ViewModel() {

    val viewState: MutableLiveData<ViewState> by lazy {
        MutableLiveData<ViewState>(ViewState())
    }

    /**
     *
     */
    fun handleIncomingData(intent: Intent) {
        when (val fieldToUpdate = incomingDataUseCase.processIntentForUpdate(intent)) {
            is Key -> {
                viewState.postValue(viewState.value?.copy(
                        sourceKey = fieldToUpdate.value
                ))
            }
            is Data -> {
                viewState.postValue(viewState.value?.copy(
                        sourceData = fieldToUpdate.value
                ))
            }
        }
    }

    fun encodeData(key: String, data: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                textEncryptionUseCase.encryptText(key, data)
            }

            viewState.postValue(viewState.value?.copy(
                    processingResult = result
            ))
        }
    }

    fun decodeData(key: String, data: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                textEncryptionUseCase.decryptText(key, data)
            }

            viewState.postValue(viewState.value?.copy(
                    processingResult = result
            ))
        }
    }
}