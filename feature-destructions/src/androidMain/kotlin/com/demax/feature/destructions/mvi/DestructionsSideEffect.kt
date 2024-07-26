package com.demax.feature.destructions.mvi

sealed interface DestructionsSideEffect {
    data object Open: DestructionsSideEffect
    data object OpenMainScreen : DestructionsSideEffect
    data object OpenRegistrationScreen : DestructionsSideEffect
    data class ShowSnackbar(val text: String) : DestructionsSideEffect
}