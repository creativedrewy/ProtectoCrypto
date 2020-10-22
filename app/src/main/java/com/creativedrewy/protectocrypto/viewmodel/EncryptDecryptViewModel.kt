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
        MutableLiveData<ViewState>(DataProcessed())
    }

    /**
     *
     */
    fun handleIncomingData(intent: Intent) {
        val fieldsUpdate = incomingDataUseCase.processIntentForUpdate(intent)

        viewState.postValue(DataProcessed(
            sourceKey = fieldsUpdate.key,
            sourceData = fieldsUpdate.data
        ))
    }

    fun encodeData(key: String, data: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    textEncryptionUseCase.encryptText(key, data)
                }

                updateProcessingResult(key, data, result)
            } catch (e: Exception) {
                viewState.postValue(ErrorState("Error encrypting your data"))
            }
        }
    }

    fun decodeData(key: String, data: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    textEncryptionUseCase.decryptText(key, data)
                }

                updateProcessingResult(key, data, result)
            } catch (e: Exception) {
                viewState.postValue(ErrorState("Error decrypting your data"))
            }
        }
    }

    private fun updateProcessingResult(key: String, data: String, processResult: String) {
        val postMe = when (viewState.value) {
            is DataProcessed -> {
                (viewState.value as? DataProcessed)?.copy(
                    processingResult = processResult
                )
            }
            is ErrorState -> {
                DataProcessed(key, data, processResult)
            }
            else -> { DataProcessed() }
        }

        viewState.postValue(postMe)
    }

    fun clearCacheIfNeeded() {
        (viewState.value as? DataProcessed)?.let {
            if (it.sourceData.isNotEmpty() &&
                it.sourceKey.isNotEmpty() &&
                it.processingResult.isNotEmpty()) {

                incomingDataUseCase.clearCachedKey()
            }
        }
    }
}