package com.creativedrewy.protectocrypto.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import com.creativedrewy.protectocrypto.usecase.IncomingDataUseCase
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
        val fieldsUpdate = incomingDataUseCase.processIntentForUpdate(intent)

        viewState.postValue(viewState.value?.copy(
            sourceKey = fieldsUpdate.key,
            sourceData = fieldsUpdate.data
        ))
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

    fun clearCacheIfNeeded() {
        viewState.value?.let {
            if (it.sourceData.isNotEmpty() &&
                it.sourceKey.isNotEmpty() &&
                it.processingResult.isNotEmpty()) {

                incomingDataUseCase.clearCachedKey()
            }
        }
    }
}