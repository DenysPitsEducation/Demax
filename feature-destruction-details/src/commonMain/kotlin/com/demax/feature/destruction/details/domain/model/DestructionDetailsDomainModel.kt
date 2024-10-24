package com.demax.feature.destruction.details.domain.model

import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.domain.model.StatusDomainModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class DestructionDetailsDomainModel(
    val id: String,
    val imageUrl: String?,
    val status: StatusDomainModel,
    val buildingType: BuildingTypeDomainModel,
    val address: String,
    val destructionStatistics: DestructionStatisticsDomainModel,
    val destructionDate: LocalDateTime,
    val description: String,
    val volunteerNeeds: List<NeedDomainModel>,
    val resourceNeeds: List<NeedDomainModel>,
)
