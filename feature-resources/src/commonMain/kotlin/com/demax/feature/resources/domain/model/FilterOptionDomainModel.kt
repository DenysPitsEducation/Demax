package com.demax.feature.resources.domain.model

import com.demax.core.domain.model.ResourceCategoryDomainModel
import com.demax.core.domain.model.StatusDomainModel

data class FilterOptionDomainModel(
    val type: Type,
    val isSelected: Boolean,
) {
    sealed class Type {
        data class Category(val category: ResourceCategoryDomainModel) : Type()
        data class Status(val status: StatusDomainModel) : Type()
    }
}
