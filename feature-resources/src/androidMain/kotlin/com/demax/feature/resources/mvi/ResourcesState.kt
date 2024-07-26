package com.demax.feature.resources.mvi

import com.demax.feature.resources.domain.model.ResourceDomainModel

data class ResourcesState(
    val searchInput: String,
    val isAdministrator: Boolean,
    val destructions: List<ResourceDomainModel>,
)
