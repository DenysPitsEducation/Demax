package com.demax.feature.resources.mvi

import com.demax.feature.resources.domain.model.FilterOptionDomainModel

sealed interface ResourcesIntent {
    data class SearchInputChanged(val input: String) : ResourcesIntent

    data object FilterClicked : ResourcesIntent

    data class FilterOptionClicked(val type: FilterOptionDomainModel.Type) : ResourcesIntent

    data object AddResourceClicked : ResourcesIntent

    data class ResourceClicked(val id: String) : ResourcesIntent
}