package com.demax.feature.destructions.domain.model

import kotlinx.datetime.LocalDate

data class DestructionDomainModel(
    val id: Long,
    val imageUrl: String?,
    val buildingType: String,
    val address: String,
    val destructionDate: LocalDate,
    val resourceProgress: Double,
    val volunteerProgress: Double,
    val specializations: List<String>,
    val status: StatusDomainModel,
) {

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
