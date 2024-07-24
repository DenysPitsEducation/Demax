package com.demax.feature.authorization.registration

sealed interface RegistrationIntent {
    data class EmailInputChanged(
        val input: String,
    ) : RegistrationIntent

    data class PasswordInputChanged(
        val input: String,
    ) : RegistrationIntent

    data class PasswordConfirmationInputChanged(
        val input: String,
    ) : RegistrationIntent

    data object CreateAccountClicked : RegistrationIntent

    data object LoginClicked : RegistrationIntent
}