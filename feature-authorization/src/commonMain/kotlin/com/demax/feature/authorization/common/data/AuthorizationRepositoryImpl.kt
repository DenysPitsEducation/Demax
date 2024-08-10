package com.demax.feature.authorization.common.data

import com.demax.feature.authorization.common.domain.AuthorizationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class AuthorizationRepositoryImpl : AuthorizationRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> = runCatching {
        Firebase.auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun register(
        email: String,
        password: String,
    ): Result<Unit> = runCatching {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun resetPassword(
        email: String,
    ): Result<Unit> = runCatching {
        Firebase.auth.sendPasswordResetEmail(email)
    }
}