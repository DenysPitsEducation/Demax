package com.demax.feature.destruction.edit.domain.model

data class NeedsDomainModel(
    val helpPackages: Int,
    val specializations: List<SpecializationDomainModel>
) {
    data class SpecializationDomainModel(
        val name: String,
        val quantity: Int,
    )
}
