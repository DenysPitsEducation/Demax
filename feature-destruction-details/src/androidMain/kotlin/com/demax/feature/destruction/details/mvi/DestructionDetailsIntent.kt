package com.demax.feature.destruction.details.mvi

sealed interface DestructionDetailsIntent {
    data class SearchInputChanged(val input: String) : DestructionDetailsIntent

    data object SortClicked : DestructionDetailsIntent

    data object FilterClicked : DestructionDetailsIntent

    data object AddDestructionClicked : DestructionDetailsIntent

    data class DestructionClicked(val id: Long) : DestructionDetailsIntent
}