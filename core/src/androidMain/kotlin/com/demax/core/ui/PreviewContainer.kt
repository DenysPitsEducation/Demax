package com.demax.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun PreviewContainer(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainer) {
            content()
        }
    }
}
