package com.demax.feature.resources.mvi

sealed interface ResourcesIntent {
    data class SearchInputChanged(val input: String) : ResourcesIntent

    data object SortClicked : ResourcesIntent

    data object FilterClicked : ResourcesIntent

    data object AddResourceClicked : ResourcesIntent

    data class ResourceClicked(val id: Long) : ResourcesIntent
}