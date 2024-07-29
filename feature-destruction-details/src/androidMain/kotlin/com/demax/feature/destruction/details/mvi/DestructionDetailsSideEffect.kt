package com.demax.feature.destruction.details.mvi

sealed interface DestructionDetailsSideEffect {
    data object Open: DestructionDetailsSideEffect
    data object OpenMainScreen : DestructionDetailsSideEffect
    data object OpenRegistrationScreen : DestructionDetailsSideEffect
    data class ShowSnackbar(val text: String) : DestructionDetailsSideEffect
}