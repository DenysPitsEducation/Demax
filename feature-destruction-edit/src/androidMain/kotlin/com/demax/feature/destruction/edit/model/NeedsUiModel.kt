package com.demax.feature.destruction.edit.model

data class NeedsUiModel(
    val helpPackages: Int,
    val specializations: List<SpecializationUiModel>
) {
    data class SpecializationUiModel(
        val name: String,
        val quantity: Int,
    )
}
