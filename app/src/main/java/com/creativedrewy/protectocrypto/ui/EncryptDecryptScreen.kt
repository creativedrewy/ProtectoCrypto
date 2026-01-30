package com.creativedrewy.protectocrypto.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creativedrewy.protectocrypto.R
import com.creativedrewy.protectocrypto.ui.theme.GradientCenter
import com.creativedrewy.protectocrypto.ui.theme.GradientEnd
import com.creativedrewy.protectocrypto.ui.theme.GradientStart
import com.creativedrewy.protectocrypto.ui.theme.InputFieldBackground
import com.creativedrewy.protectocrypto.ui.theme.ProtectoCryptoTheme
import com.creativedrewy.protectocrypto.ui.theme.White
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptUiState
import com.creativedrewy.protectocrypto.viewmodel.EncryptDecryptViewModel
import kotlinx.coroutines.launch

@Composable
fun EncryptDecryptScreen(
    viewModel: EncryptDecryptViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Handle error messages
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearErrorMessage()
        }
    }

    Box(modifier = modifier) {
        EncryptDecryptContent(
            uiState = uiState,
            onKeyChanged = viewModel::onKeyChanged,
            onDataChanged = viewModel::onDataChanged,
            onEncryptClick = viewModel::encodeData,
            onDecryptClick = viewModel::decodeData,
            onCopyClick = {
                if (viewModel.copyResultToClipboard()) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Copied result to clipboard")
                    }
                }
            },
            onClearAllClick = viewModel::clearEverything
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 56.dp)
        )
    }
}

@Composable
fun EncryptDecryptContent(
    uiState: EncryptDecryptUiState,
    onKeyChanged: (String) -> Unit,
    onDataChanged: (String) -> Unit,
    onEncryptClick: () -> Unit,
    onDecryptClick: () -> Unit,
    onCopyClick: () -> Unit,
    onClearAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
    ) {
            // Gradient header
            GradientHeader()

            // Key input field (overlapping with gradient)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-44).dp)
                    .padding(horizontal = 16.dp)
            ) {
                ProtectoTextField(
                    value = uiState.sourceKey,
                    onValueChange = onKeyChanged,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_key_24),
                            contentDescription = "Key",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Data input field
            ProtectoTextField(
                value = uiState.sourceData,
                onValueChange = onDataChanged,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_text_snippet_24),
                        contentDescription = "Data",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-20).dp)
            )

            // Encrypt and Decrypt buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onEncryptClick,
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Encrypt")
                }
                Button(
                    onClick = onDecryptClick,
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Decrypt")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Result field with copy button
            ProtectoTextField(
                value = uiState.processingResult,
                onValueChange = { /* Read only */ },
                placeholder = "Result",
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = onCopyClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_content_copy_24),
                            contentDescription = "Copy",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Clear all button
            Button(
                onClick = onClearAllClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Clear all data")
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
}

@Composable
private fun GradientHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(GradientStart, GradientCenter, GradientEnd),
                    // 135 degree angle approximation
                    start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY),
                    end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "ProtectoCrypto",
            style = MaterialTheme.typography.titleLarge,
            color = White,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun ProtectoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        readOnly = readOnly,
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = InputFieldBackground,
            unfocusedContainerColor = InputFieldBackground,
            disabledContainerColor = InputFieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}

@Preview(showBackground = true)
@Composable
private fun EncryptDecryptContentPreview() {
    ProtectoCryptoTheme {
        EncryptDecryptContent(
            uiState = EncryptDecryptUiState(
                sourceKey = "mySecretKey",
                sourceData = "Hello World",
                processingResult = "encrypted_result_here"
            ),
            onKeyChanged = {},
            onDataChanged = {},
            onEncryptClick = {},
            onDecryptClick = {},
            onCopyClick = {},
            onClearAllClick = {}
        )
    }
}
