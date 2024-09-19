package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDataModel(
    val imageUrl: String?,
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val description: String?,
    val specializations: List<String>?,
    val registrationDate: String,
    val helpsCount: Int,
)
