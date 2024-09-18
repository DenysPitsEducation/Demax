package com.demax.feature.responses.domain.model

import com.demax.core.domain.model.ResourceCategoryDomainModel

data class ResourceDomainModel(
    val id: String,
    val imageUrl: String?,
    val category: ResourceCategoryDomainModel,
    val name: String,
    val quantity: Int,
)