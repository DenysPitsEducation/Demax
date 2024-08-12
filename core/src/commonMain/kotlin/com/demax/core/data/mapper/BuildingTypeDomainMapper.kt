package com.demax.core.data.mapper

import com.demax.core.domain.model.BuildingTypeDomainModel

class BuildingTypeDomainMapper {
    fun mapToDomainModel(buildingType: String): BuildingTypeDomainModel {
        return when (buildingType) {
            "residential" -> BuildingTypeDomainModel.RESIDENTIAL
            "medical_institution" -> BuildingTypeDomainModel.MEDICAL_INSTITUTION
            "educational_institution" -> BuildingTypeDomainModel.EDUCATIONAL_INSTITUTION
            "shopping_center" -> BuildingTypeDomainModel.SHOPPING_CENTER
            "office_building" -> BuildingTypeDomainModel.OFFICE_BUILDING
            "transportation_infrastructure" -> BuildingTypeDomainModel.TRANSPORTATION_INFRASTRUCTURE
            "warehouse_building" -> BuildingTypeDomainModel.WAREHOUSE_BUILDING
            "parking_lot" -> BuildingTypeDomainModel.PARKING_LOT
            "religious_institution" -> BuildingTypeDomainModel.RELIGIOUS_INSTITUTION
            "cultural_institution" -> BuildingTypeDomainModel.CULTURAL_INSTITUTION
            "catering_facility" -> BuildingTypeDomainModel.CATERING_FACILITY
            "entertainment_complex" -> BuildingTypeDomainModel.ENTERTAINMENT_COMPLEX
            "sports_facility" -> BuildingTypeDomainModel.SPORTS_FACILITY
            else -> BuildingTypeDomainModel.UNSPECIFIED
        }
    }
}