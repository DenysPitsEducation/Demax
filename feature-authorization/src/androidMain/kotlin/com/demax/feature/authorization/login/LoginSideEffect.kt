package com.demax.feature.authorization.login

sealed interface LoginSideEffect {
    data object OpenPasswordResetScreen : LoginSideEffect
    data object OpenMainScreen : LoginSideEffect
    data object OpenRegistrationScreen : LoginSideEffect
    data class ShowSnackbar(val text: String) : LoginSideEffect
}