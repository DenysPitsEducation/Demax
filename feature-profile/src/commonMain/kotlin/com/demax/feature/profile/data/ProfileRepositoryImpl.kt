package com.demax.feature.profile.data

import com.demax.core.data.model.ProfileDataModel
import com.demax.feature.profile.domain.ProfileRepository
import com.demax.feature.profile.domain.model.ProfileDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class ProfileRepositoryImpl : ProfileRepository {
    override suspend fun getProfile(id: String): Result<ProfileDomainModel> {
        return runCatching {
            val profileCollection = Firebase.firestore.collection("profiles")
            val profileDocument = profileCollection.document(id).get()
            val profileDataModel = profileDocument.data(ProfileDataModel.serializer())
            profileDataModel.toDomainModel(id)
        }
    }

    private fun ProfileDataModel.toDomainModel(id: String): ProfileDomainModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return ProfileDomainModel(
            id = id,
            imageFile = null,
            imageUrl = imageUrl,
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            address = address,
            description = description,
            specializations = specializations,
            registrationDate = formatter.parse(registrationDate),
            helpsCount = helpsCount,
            isGuest = Firebase.auth.currentUser?.uid != id
        )
    }

    override suspend fun saveProfile(profile: ProfileDomainModel): Result<Unit> {
        return runCatching {
            val uploadedImageUrl = if (profile.imageFile != null) {
                uploadImageToStorage(profile.imageFile, profile.id)
            } else null
            val profileCollection = Firebase.firestore.collection("profiles")
            val profileDocument = profileCollection.document(profile.id)
            val profileDataModel = profile.toDataModel(uploadedImageUrl)
            profileDocument.set(profileDataModel)

            val user = Firebase.auth.currentUser
            if (user != null && user.email != profile.email) {
                user.updateEmail(profile.email)
            }
        }
    }

    private suspend fun uploadImageToStorage(image: File, profileId: String): String {
        val storageRef = Firebase.storage.reference
        val imagesStorageRef = storageRef.child("profiles/${profileId}")
        val imageStorageRef = imagesStorageRef.child("0.jpg")
        imageStorageRef.putFile(image)
        return imageStorageRef.getDownloadUrl()
    }

    private fun ProfileDomainModel.toDataModel(uploadedImageUrl: String?): ProfileDataModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return ProfileDataModel(
            imageUrl = uploadedImageUrl ?: imageUrl,
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            address = address,
            description = description,
            specializations = specializations,
            registrationDate = formatter.format(registrationDate),
            helpsCount = helpsCount
        )
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching {
            Firebase.auth.signOut()
        }
    }
}