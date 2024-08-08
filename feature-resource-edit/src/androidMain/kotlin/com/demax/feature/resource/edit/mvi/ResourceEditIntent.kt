package com.demax.feature.resource.edit.mvi

sealed interface ResourceEditIntent {
    data class SearchInputChanged(val input: String) : ResourceEditIntent

    data object SortClicked : ResourceEditIntent

    data object FilterClicked : ResourceEditIntent

    data object AddResourceClicked : ResourceEditIntent

    data class ResourceClicked(val id: Long) : ResourceEditIntent
}