package com.demax.feature.destructions.domain.model

import kotlinx.datetime.LocalDate

data class DestructionDomainModel(
    val id: String,
    val imageUrl: String?,
    val buildingType: BuildingType,
    val address: String,
    val destructionDate: LocalDate,
    val resourceProgress: Double,
    val volunteerProgress: Double,
    val specializations: List<String>,
    val status: StatusDomainModel,
    val priority: Int,
) {

    enum class BuildingType {
        RESIDENTIAL,
        MEDICAL_INSTITUTION,
        EDUCATIONAL_INSTITUTION,
        SHOPPING_CENTER,
        OFFICE_BUILDING,
        TRANSPORTATION_INFRASTRUCTURE,
        WAREHOUSE_BUILDING,
        PARKING_LOT,
        RELIGIOUS_INSTITUTION,
        CULTURAL_INSTITUTION,
        CATERING_FACILITY,
        ENTERTAINMENT_COMPLEX,
        SPORTS_FACILITY,
        UNSPECIFIED,
    }

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
