package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DestructionStatisticsDataModel(
    val destroyedFloors: String,
    val destroyedSections: String,
)
