package com.demax.core.ui.mapper

import com.demax.core.domain.model.BuildingTypeDomainModel

class BuildingTypeUiMapper {
    fun mapToUiModel(buildingType: BuildingTypeDomainModel): String {
        return when (buildingType) {
            BuildingTypeDomainModel.RESIDENTIAL -> "Житловий"
            BuildingTypeDomainModel.MEDICAL_INSTITUTION -> "Медичний заклад"
            BuildingTypeDomainModel.EDUCATIONAL_INSTITUTION -> "Освітній заклад"
            BuildingTypeDomainModel.SHOPPING_CENTER -> "Торгівельний центр"
            BuildingTypeDomainModel.OFFICE_BUILDING -> "Офісна будівля"
            BuildingTypeDomainModel.TRANSPORTATION_INFRASTRUCTURE -> "Транспортна інфраструктура"
            BuildingTypeDomainModel.WAREHOUSE_BUILDING -> "Складське приміщення"
            BuildingTypeDomainModel.PARKING_LOT -> "Парковка"
            BuildingTypeDomainModel.RELIGIOUS_INSTITUTION -> "Релігійна установа"
            BuildingTypeDomainModel.CULTURAL_INSTITUTION -> "Культурна установа"
            BuildingTypeDomainModel.CATERING_FACILITY -> "Заклад харчування"
            BuildingTypeDomainModel.ENTERTAINMENT_COMPLEX -> "Розважальний комплекс"
            BuildingTypeDomainModel.SPORTS_FACILITY -> "Спортивний об'єкт"
            BuildingTypeDomainModel.UNSPECIFIED -> "Інше"
        }
    }
}