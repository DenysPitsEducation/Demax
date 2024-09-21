package com.demax.feature.responses.model

import com.demax.feature.responses.domain.model.FilterOptionDomainModel

data class FilterOptionUiModel(
    val title: String,
    val isSelected: Boolean,
    val type: FilterOptionDomainModel.Type,
)
