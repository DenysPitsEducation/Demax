package com.demax.feature.authorization.passwordReset

sealed interface PasswordResetIntent {
    data class EmailInputChanged(val emailInput: String) : PasswordResetIntent

    data object SendClicked : PasswordResetIntent
}