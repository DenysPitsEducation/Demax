package com.demax.feature.resource.details.mvi

sealed interface ResourceDetailsSideEffect {
    data object OpenResourceHelpBottomSheet : ResourceDetailsSideEffect
    data object OpenResourceEditScreen : ResourceDetailsSideEffect
    data class ShowSnackbar(val text: String) : ResourceDetailsSideEffect
}