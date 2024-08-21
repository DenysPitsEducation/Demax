package com.demax.feature.resources.domain.model

import com.demax.core.domain.model.ResourceCategoryDomainModel

data class ResourceDomainModel(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val category: ResourceCategoryDomainModel,
    val amount: AmountDomainModel,
) {
    data class AmountDomainModel(
        val currentAmount: Int,
        val totalAmount: Int,
    )
}
