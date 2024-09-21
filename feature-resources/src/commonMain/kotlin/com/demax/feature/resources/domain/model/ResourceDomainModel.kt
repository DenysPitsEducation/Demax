package com.demax.feature.resources.domain.model

import com.demax.core.domain.model.ResourceCategoryDomainModel
import com.demax.core.domain.model.StatusDomainModel

data class ResourceDomainModel(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val category: ResourceCategoryDomainModel,
    val amount: AmountDomainModel,
    val status: StatusDomainModel,
) {
    data class AmountDomainModel(
        val currentAmount: Int,
        val totalAmount: Int,
    )
}
