package com.creativedrewy.protectocrypto.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ProtectoPrimary,
    onPrimary = White,
    primaryContainer = Purple700,
    secondary = Teal200,
    onSecondary = Black,
    secondaryContainer = Teal700,
    background = White,
    surface = White,
    onBackground = Black,
    onSurface = Black,
    surfaceVariant = InputFieldBackground
)

@Composable
fun ProtectoCryptoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        shapes = ProtectoCryptoShapes,
        content = content
    )
}
