package com.demax.feature.resource.edit.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ResourceEditPayload(
    val resourceId: String?,
)
