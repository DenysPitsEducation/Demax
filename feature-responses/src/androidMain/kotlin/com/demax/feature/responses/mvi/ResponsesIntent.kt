package com.demax.feature.responses.mvi

sealed interface ResponsesIntent {
    data class SearchInputChanged(val input: String) : ResponsesIntent

    data object SortClicked : ResponsesIntent

    data object FilterClicked : ResponsesIntent

    data object AddDestructionClicked : ResponsesIntent

    data class DestructionClicked(val id: Long) : ResponsesIntent
}