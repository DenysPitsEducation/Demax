package com.demax.feature.destruction.details.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AmountDataModel(
    val currentAmount: Int,
    val totalAmount: Int,
)
