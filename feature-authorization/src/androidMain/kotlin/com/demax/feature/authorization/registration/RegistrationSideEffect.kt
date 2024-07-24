package com.demax.feature.authorization.registration

sealed interface RegistrationSideEffect {
    data object OpenMainScreen : RegistrationSideEffect
    data object OpenLoginScreen : RegistrationSideEffect
    data class ShowSnackbar(val text: String) : RegistrationSideEffect
}