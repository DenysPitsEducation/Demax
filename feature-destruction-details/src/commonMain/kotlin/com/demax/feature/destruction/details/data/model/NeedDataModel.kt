package com.demax.feature.destruction.details.data.model

import com.demax.core.data.model.AmountDataModel
import kotlinx.serialization.Serializable

@Serializable
data class NeedDataModel(
    val name: String,
    val amount: AmountDataModel,
)