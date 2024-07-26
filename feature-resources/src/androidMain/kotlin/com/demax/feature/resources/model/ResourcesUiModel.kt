package com.demax.feature.resources.model

data class ResourcesUiModel(
    val searchInput: String,
    val showAddDestructionButton: Boolean,
    val resourceUiModels: List<ResourceUiModel>
)