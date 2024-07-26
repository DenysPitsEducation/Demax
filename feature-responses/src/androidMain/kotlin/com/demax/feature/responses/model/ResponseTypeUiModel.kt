package com.demax.feature.responses.model

sealed class ResponseTypeUiModel {
    data class Volunteer(
        val destruction: DestructionUiModel,
        val specializations: List<String>,
    ) : ResponseTypeUiModel()

    data class Resource(
        val resource: ResourceUiModel,
    ) : ResponseTypeUiModel()
}