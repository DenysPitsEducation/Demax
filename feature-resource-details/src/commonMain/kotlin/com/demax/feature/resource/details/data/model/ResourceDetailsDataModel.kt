package com.demax.feature.resource.details.data.model

import com.demax.core.data.model.AmountDataModel
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
