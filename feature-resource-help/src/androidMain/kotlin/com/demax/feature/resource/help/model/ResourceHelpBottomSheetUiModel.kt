package com.demax.feature.resource.help.model

data class ResourceHelpBottomSheetUiModel(
    val dateInputText: String?,
    val needs: List<ResourceNeedBottomSheetUiModel>,
    val isButtonEnabled: Boolean,
)
