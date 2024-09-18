package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataModel(
    val profile: Profile,
    val status: String,
    val type: String,
    val destructionId: String?,
    val specializations: List<String>?,
    val resources: List<Resource>?,
) {
    @Serializable
    data class Profile(
        val id: String,
        val name: String,
        val imageUrl: String?,
    )

    @Serializable
    data class Resource(
        val id: String,
        val quantity: Int,
    )
}
