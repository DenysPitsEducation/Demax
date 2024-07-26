package com.demax.feature.resources.model

import androidx.compose.ui.graphics.Color

data class ResourceUiModel(
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val category: String,
    val progress: ProgressUiModel,
    val status: StatusUiModel
) {

    data class ProgressUiModel(
        val progress: Double,
        val amount: String,
        val text: String,
        val color: Color,
    )
    data class StatusUiModel(
        val text: String,
        val background: Color,
    )
}
