package com.demax.feature.resources.mvi

sealed interface ResourcesSideEffect {
    data class OpenResourceDetails(val id: Long) : ResourcesSideEffect
    data class ShowSnackbar(val text: String) : ResourcesSideEffect
}