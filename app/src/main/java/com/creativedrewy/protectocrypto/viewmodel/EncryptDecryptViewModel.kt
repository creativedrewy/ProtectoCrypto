package com.creativedrewy.protectocrypto.viewmodel

import android.content.Intent
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

    val viewState: MutableLiveData<ViewState> by lazy {
        MutableLiveData<ViewState>(ViewState())
    }

    fun handleIncomingData(intent: Intent) {
        with (intent) {
            if (action == Intent.ACTION_SEND && type == "text/plain") {
                getStringExtra(Intent.EXTRA_TEXT)?.let { text ->
                    when {
                        viewState.value?.sourceKey?.isEmpty() == true -> {
                            viewState.postValue(viewState.value?.copy(
                                    sourceKey = text
                            ))
                        }
                        viewState.value?.sourceData?.isEmpty() == true -> {
                            viewState.postValue(viewState.value?.copy(
                                    sourceData = text
                            ))
                        }
                    }
                }
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