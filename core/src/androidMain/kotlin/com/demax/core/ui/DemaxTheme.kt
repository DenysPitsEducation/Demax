package com.demax.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("No Snackbar Host State") }

@Composable
fun DemaxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            tertiary = Color(0xFF3700B3)
        )
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
