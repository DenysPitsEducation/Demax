package com.demax.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ResourceHelpPayload(
    val resources: List<Resource>
) {
    @Serializable
    data class Resource(
        val id: String,
        val name: String,
    )
}
