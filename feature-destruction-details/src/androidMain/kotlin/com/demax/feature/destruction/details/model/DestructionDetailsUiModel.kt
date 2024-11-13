package com.demax.feature.destruction.details.model

data class DestructionDetailsUiModel(
    val imageUrl: String?,
    val status: String,
    val buildingType: String,
    val address: String,
    val destructionStatistics: DestructionStatisticsUiModel,
    val destructionDate: String,
    val description: String,
    val helpPackagesCount: String?,
    val volunteerNeedsBlock: VolunteerNeedsBlockUiModel,
    val resourceNeedsBlock: ResourceNeedsBlockUiModel,
)