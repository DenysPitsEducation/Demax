package com.demax.feature.profile.model

data class ProfileUiModel(
    val imageUrl: String?,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val about: String,
    val specializations: List<String>,
    val registrationDate: String,
    val helpsCount: Int,
)