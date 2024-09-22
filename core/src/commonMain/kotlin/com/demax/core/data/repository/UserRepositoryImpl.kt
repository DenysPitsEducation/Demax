package com.demax.core.data.repository

import com.demax.core.data.model.ProfileDataModel
import com.demax.core.domain.model.UserDomainModel
import com.demax.core.domain.repository.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class UserRepositoryImpl : UserRepository {
    private val userFlow: MutableStateFlow<UserDomainModel?> = MutableStateFlow(null)

    override suspend fun initUser() {
        val authorizationFlow = Firebase.auth.authStateChanged
        authorizationFlow.collect { user ->
            if (user != null) {
                val profileCollection = Firebase.firestore.collection("profiles")
                val profileDocument = profileCollection.document(user.uid).get()
                val profileDataModel = profileDocument.data(ProfileDataModel.serializer())
                val administratorEmails = getAdministratorEmails()
                userFlow.value = UserDomainModel(
                    id = user.uid,
                    email = profileDataModel.email,
                    isAdministrator = profileDataModel.email in administratorEmails,
                )
            } else {
                userFlow.value = null
            }
        }

    }

    override fun getUserFlow(): Flow<UserDomainModel?> {
        return userFlow
    }

    private fun getAdministratorEmails() = listOf(
        "lykoshko4@gmail.com"
    )
}