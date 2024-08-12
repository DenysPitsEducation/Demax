package com.demax.feature.destruction.details.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DestructionDetailsDataModel(
    val imageUrl: String?,
    val status: String,
    val buildingType: String,
    val address: String,
    val destructionStatistics: DestructionStatisticsDataModel,
    val destructionDate: String,
    val description: String,
    val volunteerNeeds: List<NeedDataModel>,
    val resourceNeeds: List<NeedDataModel>,
)
