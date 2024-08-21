package com.demax.core.ui.mapper

import com.demax.core.domain.model.ResourceCategoryDomainModel

class ResourceCategoryUiMapper {

    fun mapToUiModel(category: ResourceCategoryDomainModel): String {
        return when (category) {
            ResourceCategoryDomainModel.MEDICAL_PRODUCTS -> "Медичні засоби"
            ResourceCategoryDomainModel.FOOD -> "Продукти харчування"
            ResourceCategoryDomainModel.CLOTHES -> "Одяг та особисті речі"
            ResourceCategoryDomainModel.PERSONAL_CARE -> "Засоби особистої гігієні"
            ResourceCategoryDomainModel.TOOLS -> "Інструменти та обладнання"
            ResourceCategoryDomainModel.RESCUE_EQUIPMENT -> "Рятувальне обладнання"
            ResourceCategoryDomainModel.VEHICLES_AND_FUEL -> "Транспортні засоби та пальне"
            ResourceCategoryDomainModel.ELECTRONIC_DEVICES -> "Електронні пристрої"
            ResourceCategoryDomainModel.OTHER -> "Інше"
        }
    }
}