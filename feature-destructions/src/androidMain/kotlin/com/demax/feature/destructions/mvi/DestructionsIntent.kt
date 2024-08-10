package com.demax.feature.destructions.mvi

import com.demax.feature.destructions.domain.model.FilterOptionDomainModel
import com.demax.feature.destructions.domain.model.SortingTypeDomainModel

sealed interface DestructionsIntent {
    data object SortClicked : DestructionsIntent

    data class SortItemClicked(val type: SortingTypeDomainModel) : DestructionsIntent

    data object FilterClicked : DestructionsIntent

    data class FilterOptionClicked(val type: FilterOptionDomainModel.Type) : DestructionsIntent

    data object AddDestructionClicked : DestructionsIntent

    data class DestructionClicked(val id: String) : DestructionsIntent
}