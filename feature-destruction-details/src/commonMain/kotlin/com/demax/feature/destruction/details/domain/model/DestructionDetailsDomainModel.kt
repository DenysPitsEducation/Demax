package com.demax.feature.destruction.details.domain.model

import kotlinx.datetime.LocalDate

data class DestructionDetailsDomainModel(
    val id: String,
    val imageUrl: String?,
    val status: StatusDomainModel,
    val buildingType: String,
    val address: String,
    val destructionStatistics: DestructionStatisticsDomainModel,
    val destructionDate: LocalDate,
    val description: String,
    val volunteerNeeds: List<NeedDomainModel>,
    val resourceNeeds: List<NeedDomainModel>,
) {

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
