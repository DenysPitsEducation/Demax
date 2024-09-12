package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NeedDataModel(
    val name: String,
    val amount: AmountDataModel,
)
