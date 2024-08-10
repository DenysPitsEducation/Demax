package com.demax.feature.destructions.model

import com.demax.feature.destructions.domain.model.SortingTypeDomainModel

data class SortUiModel(
    val type: SortingTypeDomainModel,
    val title: String,
    val isSelected: Boolean,
)
