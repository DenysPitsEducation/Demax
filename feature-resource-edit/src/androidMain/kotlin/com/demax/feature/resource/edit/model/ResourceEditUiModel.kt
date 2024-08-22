package com.demax.feature.resource.edit.model

data class ResourceEditUiModel(
    val image: Any?,
    val name: String,
    val category: String,
    val dropDownCategories: List<CategoryUiModel>,
    val totalAmount: String,
    val currentAmount: String,
    val description: String,
    val destruction: String,
    val dropDownDestructions: List<DestructionUiModel>
)