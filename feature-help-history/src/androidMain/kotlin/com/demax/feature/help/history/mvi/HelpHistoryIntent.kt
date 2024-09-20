package com.demax.feature.help.history.mvi

sealed interface HelpHistoryIntent {
    data class DestructionClicked(val destructionId: String) :
        HelpHistoryIntent

    data class ResourceClicked(val resourceId: String) :
        HelpHistoryIntent
}