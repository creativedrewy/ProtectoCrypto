package com.creativedrewy.protectocrypto.viewmodel

sealed class ViewState

data class DataProcessed(
    val sourceKey: String = "",
    val sourceData: String = "",
    val processingResult: String = ""
) : ViewState()

data class ErrorState(
    val message: String = ""
): ViewState()