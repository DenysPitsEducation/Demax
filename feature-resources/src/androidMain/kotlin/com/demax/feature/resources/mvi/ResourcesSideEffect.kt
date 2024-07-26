package com.demax.feature.resources.mvi

sealed interface ResourcesSideEffect {
    data object Open: ResourcesSideEffect
    data object OpenMainScreen : ResourcesSideEffect
    data object OpenRegistrationScreen : ResourcesSideEffect
    data class ShowSnackbar(val text: String) : ResourcesSideEffect
}