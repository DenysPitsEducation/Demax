package com.demax.feature.profile.domain

import com.demax.feature.profile.domain.model.ProfileDomainModel

interface ProfileRepository {
    suspend fun getProfile(id: String): Result<ProfileDomainModel>
    suspend fun saveProfile(profile: ProfileDomainModel): Result<Unit>
}