package com.demax.core.domain.model

data class UserDomainModel(
    val id: String,
    val email: String,
    val isAdministrator: Boolean,
)
