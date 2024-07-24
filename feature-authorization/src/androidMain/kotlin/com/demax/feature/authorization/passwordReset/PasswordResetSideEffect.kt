package com.demax.feature.authorization.passwordReset

sealed interface PasswordResetSideEffect {
    data class ShowSnackbar(
        val text: String
    ) : PasswordResetSideEffect
}