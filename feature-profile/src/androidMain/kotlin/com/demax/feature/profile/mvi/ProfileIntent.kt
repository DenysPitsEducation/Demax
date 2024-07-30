package com.demax.feature.profile.mvi

sealed interface ProfileIntent {
    data class SearchInputChanged(val input: String) : ProfileIntent

    data object SortClicked : ProfileIntent

    data object FilterClicked : ProfileIntent

    data object AddDestructionClicked : ProfileIntent

    data class DestructionClicked(val id: Long) : ProfileIntent
}