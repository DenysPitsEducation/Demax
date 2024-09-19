package com.demax.feature.authorization.common.domain

interface AuthorizationRepository {
    suspend fun login(
        email: String,
        password: String,
    ): Result<Unit>

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): Result<Unit>

    suspend fun resetPassword(
        email: String,
    ): Result<Unit>
}