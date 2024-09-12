package com.demax.feature.destruction.edit.model

import com.demax.core.domain.model.BuildingTypeDomainModel

data class BuildingTypeUiModel(
    val type: BuildingTypeDomainModel,
    val title: String,
)
