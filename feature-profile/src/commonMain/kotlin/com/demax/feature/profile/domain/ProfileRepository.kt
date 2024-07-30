package com.demax.feature.profile.domain

import com.demax.feature.profile.domain.model.ProfileDomainModel

interface ProfileRepository {
    suspend fun getDestructionDetails(): Result<ProfileDomainModel>
}