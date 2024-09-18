package com.demax.feature.resource.help.mvi

sealed interface ResourceHelpSideEffect {
    data object OpenResourceHelpDatePicker : ResourceHelpSideEffect
    data class ShowMessage(val text: String) : ResourceHelpSideEffect
    data object DismissBottomSheet : ResourceHelpSideEffect
}