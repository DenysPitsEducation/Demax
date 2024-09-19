package com.demax.feature.profile.model

data class ProfileUiModel(
    val isGuest: Boolean,
    val image: Any?,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val description: String,
    val specializations: List<String>,
    val registrationDate: String,
    val helpsCount: Int,
)