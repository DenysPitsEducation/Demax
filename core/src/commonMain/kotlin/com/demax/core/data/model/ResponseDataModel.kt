package com.demax.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataModel(
    val profileId: String,
    val status: String,
    val type: String,
    val destructionId: String?,
    val specializations: List<String>?,
    val resources: List<Resource>?,
) {
    @Serializable
    data class Resource(
        val id: String,
        val quantity: Int,
    )
}
