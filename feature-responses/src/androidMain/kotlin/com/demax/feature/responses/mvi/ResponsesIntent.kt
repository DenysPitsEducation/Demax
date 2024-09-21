package com.demax.feature.responses.mvi

import com.demax.feature.responses.domain.model.FilterOptionDomainModel

sealed interface ResponsesIntent {
    data object FilterClicked : ResponsesIntent

    data class FilterOptionClicked(val type: FilterOptionDomainModel.Type) : ResponsesIntent

    data class ProfileClicked(val profileId: String) : ResponsesIntent

    data class DestructionClicked(val destructionId: String) : ResponsesIntent

    data class ResourceClicked(val resourceId: String) : ResponsesIntent

    data class ApproveButtonClicked(val responseId: String) : ResponsesIntent

    data class RejectButtonClicked(val responseId: String) : ResponsesIntent
}