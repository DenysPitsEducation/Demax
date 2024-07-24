package com.demax.feature.authorization.login

sealed interface LoginIntent {
    data class EmailInputChanged(val emailInput: String) : LoginIntent

    data class PasswordInputChanged(val passwordInput: String) : LoginIntent

    data object OpenPasswordResetScreen : LoginIntent

    data object CreateAccountClicked : LoginIntent

    data object LoginClicked : LoginIntent
}