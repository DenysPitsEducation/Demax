package com.demax.feature.destructions.domain.model

data class FilterOptionDomainModel(
    val type: Type,
    val isSelected: Boolean,
) {
    sealed class Type {
        data class BuildingType(val buildingType: DestructionDomainModel.BuildingType) : Type()
        data class Status(val status: DestructionDomainModel.StatusDomainModel) : Type()
        data class Specialization(val specialization: String) : Type()
    }
}
