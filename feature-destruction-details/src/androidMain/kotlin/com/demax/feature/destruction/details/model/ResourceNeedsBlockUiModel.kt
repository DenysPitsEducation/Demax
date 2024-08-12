package com.demax.feature.destruction.details.model

data class ResourceNeedsBlockUiModel(
    val needs: List<NeedUiModel>,
    val showHelpButton: Boolean,
    val showAddResourcesButton: Boolean,
)
