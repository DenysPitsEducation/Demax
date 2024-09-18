package com.demax.feature.responses.mvi

sealed interface ResponsesIntent {
    data object SortClicked : ResponsesIntent

    data object FilterClicked : ResponsesIntent

    data class ProfileClicked(val profileId: String) : ResponsesIntent

    data class DestructionClicked(val destructionId: String) : ResponsesIntent

    data class ResourceClicked(val resourceId: String) : ResponsesIntent

    data class ApproveButtonClicked(val responseId: String) : ResponsesIntent

    data class RejectButtonClicked(val responseId: String) : ResponsesIntent
}