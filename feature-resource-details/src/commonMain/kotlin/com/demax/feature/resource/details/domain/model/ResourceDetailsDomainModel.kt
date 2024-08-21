package com.demax.feature.resource.details.domain.model

import com.demax.core.domain.model.ResourceCategoryDomainModel

data class ResourceDetailsDomainModel(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val category: ResourceCategoryDomainModel,
    val amount: AmountDomainModel,
    val description: String,
    val destruction: DestructionDomainModel,
)
