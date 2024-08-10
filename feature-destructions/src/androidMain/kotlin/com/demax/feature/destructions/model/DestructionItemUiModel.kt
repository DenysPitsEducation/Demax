package com.demax.feature.destructions.model

import androidx.compose.ui.graphics.Color

data class DestructionItemUiModel(
    val id: String,
    val imageUrl: String?,
    val buildingType: String,
    val address: String,
    val destructionDate: String,
    val resourceProgress: ProgressUiModel,
    val volunteerProgress: ProgressUiModel,
    val specializations: List<String>,
    val status: StatusUiModel
) {

    data class ProgressUiModel(
        val progress: Double,
        val percentage: String,
        val text: String,
        val color: Color,
    )
    data class StatusUiModel(
        val text: String,
        val background: Color,
    )
}
