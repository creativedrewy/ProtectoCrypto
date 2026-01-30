package com.creativedrewy.protectocrypto.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creativedrewy.dataencryption.TextEncryptDecryptUseCase
import com.creativedrewy.protectocrypto.usecase.IncomingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EncryptDecryptViewModel @Inject constructor(
    private val textEncryptionUseCase: TextEncryptDecryptUseCase,
    private val incomingDataUseCase: IncomingDataUseCase,
    private val clipboardManager: ClipboardManager
) : ViewModel() {

    companion object {
        const val CLIP_LABEL = "ProtectoCrypto"
    }

    private val _uiState = MutableStateFlow(EncryptDecryptUiState())
    val uiState: StateFlow<EncryptDecryptUiState> = _uiState.asStateFlow()

    fun handleIncomingData(intent: Intent) {
        val fieldsUpdate = incomingDataUseCase.processIntentForUpdate(intent)
        _uiState.update { currentState ->
            currentState.copy(
                sourceKey = fieldsUpdate.key,
                sourceData = fieldsUpdate.data
            )
        }
    }

    fun onKeyChanged(key: String) {
        _uiState.update { it.copy(sourceKey = key) }
    }

    fun onDataChanged(data: String) {
        _uiState.update { it.copy(sourceData = data) }
    }

    fun encodeData() {
        val currentState = _uiState.value
        if (currentState.sourceKey.isBlank() || currentState.sourceData.isBlank()) return

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    textEncryptionUseCase.encryptText(currentState.sourceKey, currentState.sourceData)
                }
                _uiState.update { it.copy(processingResult = result, errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error encrypting your data") }
            }
        }
    }

    fun decodeData() {
        val currentState = _uiState.value
        if (currentState.sourceKey.isBlank() || currentState.sourceData.isBlank()) return

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    textEncryptionUseCase.decryptText(currentState.sourceKey, currentState.sourceData)
                }
                _uiState.update { it.copy(processingResult = result, errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error decrypting your data") }
            }
        }
    }

    fun copyResultToClipboard(): Boolean {
        val result = _uiState.value.processingResult
        if (result.isNotEmpty()) {
            val clipData = ClipData.newPlainText(CLIP_LABEL, result)
            clipboardManager.setPrimaryClip(clipData)
            return true
        }
        return false
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun clearCacheIfNeeded() {
        val state = _uiState.value
        if (state.sourceData.isNotEmpty() &&
            state.sourceKey.isNotEmpty() &&
            state.processingResult.isNotEmpty()) {
            incomingDataUseCase.clearCachedKey()
        }
    }

    /**
     * Clear the form, cached values and clipboard
     */
    fun clearEverything() {
        incomingDataUseCase.clearCachedKey()
        clipboardManager.setPrimaryClip(ClipData.newPlainText(CLIP_LABEL, " "))
        _uiState.value = EncryptDecryptUiState()
    }
}