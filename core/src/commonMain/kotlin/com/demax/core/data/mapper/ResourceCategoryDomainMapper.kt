package com.demax.core.data.mapper

import com.demax.core.domain.model.ResourceCategoryDomainModel

class ResourceCategoryDomainMapper {
    fun mapToDomainModel(category: String): ResourceCategoryDomainModel {
        return when (category) {
            "medical_products" -> ResourceCategoryDomainModel.MEDICAL_PRODUCTS
            "food" -> ResourceCategoryDomainModel.FOOD
            "clothes" -> ResourceCategoryDomainModel.CLOTHES
            "personal_care" -> ResourceCategoryDomainModel.PERSONAL_CARE
            "tools" -> ResourceCategoryDomainModel.TOOLS
            "rescue_equipment" -> ResourceCategoryDomainModel.RESCUE_EQUIPMENT
            "vehicles_and_fuel" -> ResourceCategoryDomainModel.VEHICLES_AND_FUEL
            "electronic_devices" -> ResourceCategoryDomainModel.ELECTRONIC_DEVICES
            else -> ResourceCategoryDomainModel.OTHER
        }
    }

    fun mapToDataModel(category: ResourceCategoryDomainModel): String {
        return when (category) {
            ResourceCategoryDomainModel.MEDICAL_PRODUCTS -> "medical_products"
            ResourceCategoryDomainModel.FOOD -> "food"
            ResourceCategoryDomainModel.CLOTHES -> "clothes"
            ResourceCategoryDomainModel.PERSONAL_CARE -> "personal_care"
            ResourceCategoryDomainModel.TOOLS -> "tools"
            ResourceCategoryDomainModel.RESCUE_EQUIPMENT -> "rescue_equipment"
            ResourceCategoryDomainModel.VEHICLES_AND_FUEL -> "vehicles_and_fuel"
            ResourceCategoryDomainModel.ELECTRONIC_DEVICES -> "electronic_devices"
            ResourceCategoryDomainModel.OTHER -> "other"
        }
    }
}