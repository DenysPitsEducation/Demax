package com.demax.feature.destruction.details.data.model

import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class NeedDataModel(
    val name: String,
    val amount: AmountDataModel,
)
