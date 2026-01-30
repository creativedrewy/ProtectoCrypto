package com.creativedrewy.protectocrypto.viewmodel

/**
 * UI state for the encrypt/decrypt screen
 */
data class EncryptDecryptUiState(
    val sourceKey: String = "",
    val sourceData: String = "",
    val processingResult: String = "",
    val errorMessage: String? = null
)