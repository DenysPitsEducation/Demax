package com.demax.feature.destructions

sealed interface DestructionsIntent {
    data class EmailInputChanged(val emailInput: String) : DestructionsIntent

    data class PasswordInputChanged(val passwordInput: String) : DestructionsIntent

    data object OpenPasswordResetScreen : DestructionsIntent

    data object CreateAccountClicked : DestructionsIntent

    data object LoginClicked : DestructionsIntent
}