package com.demax.feature.destruction.details.model

data class ResourceHelpBottomSheetUiModel(
    val dateInputText: String?,
    val needs: List<ResourceNeedBottomSheetUiModel>,
    val isButtonEnabled: Boolean,
)
