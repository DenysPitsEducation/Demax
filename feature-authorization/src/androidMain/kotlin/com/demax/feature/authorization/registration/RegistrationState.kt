package com.demax.feature.authorization.registration

data class RegistrationState(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
)
