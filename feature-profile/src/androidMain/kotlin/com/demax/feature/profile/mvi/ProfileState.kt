package com.demax.feature.profile.mvi

import com.demax.feature.profile.domain.model.ProfileDomainModel

data class ProfileState(
    val profile: ProfileDomainModel?,
)
