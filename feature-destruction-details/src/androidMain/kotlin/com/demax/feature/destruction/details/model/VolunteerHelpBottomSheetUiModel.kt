package com.demax.feature.destruction.details.model

data class VolunteerHelpBottomSheetUiModel(
    val dateInputText: String?,
    val needs: List<VolunteerNeedBottomSheetUiModel>,
    val isButtonEnabled: Boolean,
)
