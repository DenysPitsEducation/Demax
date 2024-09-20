package com.demax.feature.help.history.mvi

sealed interface HelpHistorySideEffect {
    data class OpenProfile(val id: String): HelpHistorySideEffect
    data class OpenDestructionDetails(val id: String): HelpHistorySideEffect
    data class OpenResourceDetails(val id: String): HelpHistorySideEffect
    data class ShowSnackbar(val text: String) : HelpHistorySideEffect
}