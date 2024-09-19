package com.demax.feature.authorization.common.data

import com.demax.core.data.model.ProfileDataModel
import com.demax.feature.authorization.common.domain.AuthorizationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

class AuthorizationRepositoryImpl : AuthorizationRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> = runCatching {
        Firebase.auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): Result<Unit> = runCatching {
        val result = Firebase.auth.createUserWithEmailAndPassword(email, password)
        val user = result.user
        if (user != null) {
            val id= user.uid
            val profilesCollection = Firebase.firestore.collection("profiles")
            val nowDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
            profilesCollection.document(id).set(
                ProfileDataModel(
                    imageUrl = null,
                    name = name,
                    email = email,
                    phoneNumber = null,
                    address = null,
                    description = null,
                    specializations = listOf("Психотерапія", "Керування вантажівкою"),
                    registrationDate = formatter.format(nowDate),
                    helpsCount = 0,
                )
            )
        }
    }

    override suspend fun resetPassword(
        email: String,
    ): Result<Unit> = runCatching {
        Firebase.auth.sendPasswordResetEmail(email)
    }
}