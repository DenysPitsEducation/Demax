package com.demax.feature.resources.model

import com.demax.feature.resources.domain.model.FilterOptionDomainModel

data class FilterOptionUiModel(
    val title: String,
    val isSelected: Boolean,
    val type: FilterOptionDomainModel.Type,
)
