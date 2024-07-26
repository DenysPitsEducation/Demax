package com.demax.feature.responses.mvi

sealed interface ResponsesSideEffect {
    data object Open: ResponsesSideEffect
    data object OpenMainScreen : ResponsesSideEffect
    data object OpenRegistrationScreen : ResponsesSideEffect
    data class ShowSnackbar(val text: String) : ResponsesSideEffect
}