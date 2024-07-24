package com.demax.feature.authorization.common.data

import android.util.Log
import com.demax.feature.authorization.common.domain.AuthorizationRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthorizationRepositoryImpl : AuthorizationRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> = runCatching {
        suspendCoroutine { continuation ->
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun register(
        email: String,
        password: String,
    ): Result<Unit> = runCatching {
        suspendCoroutine { continuation ->
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun resetPassword(
        email: String,
    ): Result<Unit> = runCatching {
        suspendCoroutine { continuation ->
            Firebase.auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}