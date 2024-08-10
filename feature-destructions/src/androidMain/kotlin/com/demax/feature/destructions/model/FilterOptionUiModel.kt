package com.demax.feature.destructions.model

import com.demax.feature.destructions.domain.model.FilterOptionDomainModel

data class FilterOptionUiModel(
    val title: String,
    val isSelected: Boolean,
    val type: FilterOptionDomainModel.Type,
)
