package com.demax.feature.profile.domain.model

import dev.gitlive.firebase.storage.File
import kotlinx.datetime.LocalDate

data class ProfileDomainModel(
    val isGuest: Boolean,
    val id: String,
    val imageFile: File?,
    val imageUrl: String?,
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val description: String?,
    val specializations: List<String>?,
    val registrationDate: LocalDate,
    val helpsCount: Int,
)
