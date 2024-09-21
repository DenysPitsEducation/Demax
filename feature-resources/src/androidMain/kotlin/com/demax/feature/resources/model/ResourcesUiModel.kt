package com.demax.feature.resources.model

data class ResourcesUiModel(
    val searchInput: String,
    val filterUiModels: List<FilterUiModel>,
    val showAddDestructionButton: Boolean,
    val resourceUiModels: List<ResourceUiModel>
)