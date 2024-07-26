package com.demax.feature.resources.mvi

sealed interface ResourcesIntent {
    data class SearchInputChanged(val input: String) : ResourcesIntent

    data object SortClicked : ResourcesIntent

    data object FilterClicked : ResourcesIntent

    data object AddDestructionClicked : ResourcesIntent

    data class DestructionClicked(val id: Long) : ResourcesIntent
}