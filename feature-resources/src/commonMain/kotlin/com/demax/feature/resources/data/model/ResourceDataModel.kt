package com.demax.feature.resources.data.model

import com.demax.core.data.model.AmountDataModel
import kotlinx.serialization.Serializable

@Serializable
data class ResourceDataModel(
    val imageUrl: String?,
    val name: String,
    val category: String,
    val amount: AmountDataModel,
)
