package com.demax.feature.authorization.registration

data class RegistrationState(
    val email: String,
    val password: String,
    val passwordConfirmation: String,
)
