package com.demax.feature.resource.edit.model

import com.demax.core.domain.model.ResourceCategoryDomainModel

data class CategoryUiModel(
    val type: ResourceCategoryDomainModel,
    val title: String,
)
