package com.demax.feature.destructions.domain.model

import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.domain.model.StatusDomainModel

data class FilterOptionDomainModel(
    val type: Type,
    val isSelected: Boolean,
) {
    sealed class Type {
        data class BuildingType(val buildingType: BuildingTypeDomainModel) : Type()
        data class Status(val status: StatusDomainModel) : Type()
        data class Specialization(val specialization: String) : Type()
    }
}
