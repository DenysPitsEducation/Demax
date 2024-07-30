package com.demax.feature.resource.details.model

data class ResourceDetailsUiModel(
    val imageUrl: String?,
    val status: String,
    val name: String,
    val category: String,
    val amount: AmountUiModel,
    val description: String,
    val destruction: DestructionUiModel,
    val showEditButton: Boolean,
)