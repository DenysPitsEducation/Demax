package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DestructionDataModel(
    val imageUrl: String?,
    val buildingType: String,
    val address: String,
    val destructionStatistics: DestructionStatisticsDataModel,
    val destructionDate: String,
    val description: String,
    val volunteerNeeds: List<VolunteerNeedDataModel>,
    val resourceNeeds: List<String>,
    val priority: Int,
)
