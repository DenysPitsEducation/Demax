package com.demax.feature.resource.edit.model

data class ResourceEditUiModel(
    val imageUrl: String?,
    val status: String,
    val name: String,
    val category: String,
    val amount: Int,
    val description: String,
    val destruction: String,
)