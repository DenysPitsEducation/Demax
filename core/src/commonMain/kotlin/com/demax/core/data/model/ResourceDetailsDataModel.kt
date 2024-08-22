package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResourceDetailsDataModel(
    val imageUrl: String?,
    val name: String,
    val category: String,
    val amount: AmountDataModel,
    val description: String,
    val destructionId: String,
)
