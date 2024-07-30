package com.demax.feature.resource.details.mvi

sealed interface ResourceDetailsSideEffect {
    data class ShowSnackbar(val text: String) : ResourceDetailsSideEffect
}