package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResourceDataModel(
    val imageUrl: String?,
    val name: String,
    val category: String,
    val amount: AmountDataModel,
)
