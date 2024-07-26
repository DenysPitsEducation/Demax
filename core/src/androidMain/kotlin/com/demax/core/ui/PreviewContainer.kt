package com.demax.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PreviewContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier,
        ) {
            content()
        }
    }
}
