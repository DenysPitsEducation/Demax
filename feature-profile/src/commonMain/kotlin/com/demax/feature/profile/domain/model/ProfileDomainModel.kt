package com.demax.feature.profile.domain.model

import kotlinx.datetime.LocalDate

data class ProfileDomainModel(
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val about: String,
    val specializations: List<String>,
    val registrationDate: LocalDate,
    val helpsCount: Int,
)
