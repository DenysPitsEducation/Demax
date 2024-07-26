package com.demax.feature.responses.model

data class ResponseUiModel(
    val id: Long,
    val profile: ProfileUiModel,
    val type: ResponseTypeUiModel,
    val status: StatusUiModel,
    val showConfirmationButtons: Boolean,
)
