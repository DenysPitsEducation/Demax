package com.demax.feature.destruction.details.model

import androidx.compose.ui.graphics.Color

data class ProgressUiModel(
    val progress: Double,
    val amount: String,
    val text: String,
    val color: Color,
)
