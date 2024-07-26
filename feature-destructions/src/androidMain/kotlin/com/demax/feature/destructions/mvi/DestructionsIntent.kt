package com.demax.feature.destructions.mvi

sealed interface DestructionsIntent {
    data class SearchInputChanged(val input: String) : DestructionsIntent

    data object SortClicked : DestructionsIntent

    data object FilterClicked : DestructionsIntent

    data object AddDestructionClicked : DestructionsIntent

    data class DestructionClicked(val id: Long) : DestructionsIntent
}