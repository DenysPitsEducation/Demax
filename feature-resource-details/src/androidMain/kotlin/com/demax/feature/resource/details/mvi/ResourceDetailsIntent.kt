package com.demax.feature.resource.details.mvi

sealed interface ResourceDetailsIntent {
    data class SearchInputChanged(val input: String) : ResourceDetailsIntent

    data object SortClicked : ResourceDetailsIntent

    data object FilterClicked : ResourceDetailsIntent

    data object AddResourceClicked : ResourceDetailsIntent

    data class ResourceClicked(val id: Long) : ResourceDetailsIntent
}