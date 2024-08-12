package com.demax.feature.destructions.domain.model

import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.domain.model.StatusDomainModel
import kotlinx.datetime.LocalDate

data class DestructionDomainModel(
    val id: String,
    val imageUrl: String?,
    val buildingType: BuildingTypeDomainModel,
    val address: String,
    val destructionDate: LocalDate,
    val resourceProgress: Double,
    val volunteerProgress: Double,
    val specializations: List<String>,
    val status: StatusDomainModel,
    val priority: Int,
)
