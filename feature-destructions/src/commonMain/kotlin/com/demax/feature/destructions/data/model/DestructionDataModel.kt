package com.demax.feature.destructions.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DestructionDataModel(
    val imageUrl: String?,
    val buildingType: String,
    val address: String,
    val destructionDate: String,
    val resourceProgress: Double,
    val volunteerProgress: Double,
    val specializations: List<String>,
    val status: String,
    val priority: Int,
)
