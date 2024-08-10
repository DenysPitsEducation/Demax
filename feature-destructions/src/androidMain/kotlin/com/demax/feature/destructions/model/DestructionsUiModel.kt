package com.demax.feature.destructions.model

data class DestructionsUiModel(
    val sortUiModels: List<SortUiModel>,
    val filterUiModels: List<FilterUiModel>,
    val showAddDestructionButton: Boolean,
    val destructionItemUiModels: List<DestructionItemUiModel>
)