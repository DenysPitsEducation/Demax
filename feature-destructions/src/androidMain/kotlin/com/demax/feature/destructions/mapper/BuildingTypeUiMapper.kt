package com.demax.feature.destructions.mapper

import com.demax.feature.destructions.domain.model.DestructionDomainModel

internal class BuildingTypeUiMapper {
    fun mapToUiModel(buildingType: DestructionDomainModel.BuildingType): String {
        return when (buildingType) {
            DestructionDomainModel.BuildingType.RESIDENTIAL -> "Житловий"
            DestructionDomainModel.BuildingType.MEDICAL_INSTITUTION -> "Медичний заклад"
            DestructionDomainModel.BuildingType.EDUCATIONAL_INSTITUTION -> "Освітній заклад"
            DestructionDomainModel.BuildingType.SHOPPING_CENTER -> "Торгівельний центр"
            DestructionDomainModel.BuildingType.OFFICE_BUILDING -> "Офісна будівля"
            DestructionDomainModel.BuildingType.TRANSPORTATION_INFRASTRUCTURE -> "Транспортна інфраструктура"
            DestructionDomainModel.BuildingType.WAREHOUSE_BUILDING -> "Складське приміщення"
            DestructionDomainModel.BuildingType.PARKING_LOT -> "Парковка"
            DestructionDomainModel.BuildingType.RELIGIOUS_INSTITUTION -> "Релігійна установа"
            DestructionDomainModel.BuildingType.CULTURAL_INSTITUTION -> "Культурна установа"
            DestructionDomainModel.BuildingType.CATERING_FACILITY -> "Заклад харчування"
            DestructionDomainModel.BuildingType.ENTERTAINMENT_COMPLEX -> "Розважальний комплекс"
            DestructionDomainModel.BuildingType.SPORTS_FACILITY -> "Спортивний об'єкт"
            DestructionDomainModel.BuildingType.UNSPECIFIED -> "Інше"
        }
    }
}