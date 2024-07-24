package com.demax.feature.destructions

sealed interface DestructionsSideEffect {
    data object OpenPasswordResetScreen : DestructionsSideEffect
    data object OpenMainScreen : DestructionsSideEffect
    data object OpenRegistrationScreen : DestructionsSideEffect
    data class ShowSnackbar(val text: String) : DestructionsSideEffect
}