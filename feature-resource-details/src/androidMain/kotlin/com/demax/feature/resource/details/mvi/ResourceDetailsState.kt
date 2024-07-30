package com.demax.feature.resource.details.mvi

import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel

data class ResourceDetailsState(
    val resourceDetails: ResourceDetailsDomainModel?,
    val isAdministrator: Boolean,
)
