package com.demax.feature.destructions.model

data class DestructionsUiModel(
    val searchInput: String,
    val showAddDestructionButton: Boolean,
    val destructionItemUiModels: List<DestructionItemUiModel>
)