package com.example.csc475_ct8_final

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB4A2),
    secondary = Color(0xFFE7BDB2),
    tertiary = Color(0xFFD8C4A0),
    background = Color(0xFF201A18),
    surface = Color(0xFF201A18),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA9381F),
    secondary = Color(0xFF77574E),
    tertiary = Color(0xFF6C5D3F),
    background = Color(0xFFFFFBFF),
    surface = Color(0xFFFFFBFF),
)

@Composable
fun HCookBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
