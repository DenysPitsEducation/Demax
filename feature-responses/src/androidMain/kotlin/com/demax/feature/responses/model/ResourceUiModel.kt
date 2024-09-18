package com.demax.feature.responses.model

data class ResourceUiModel(
    val id: String,
    val imageUrl: String?,
    val category: String,
    val name: String,
    val quantity: Int,
)