package com.demax.feature.help.history.model

sealed class ResponseTypeUiModel {
    data class Volunteer(
        val destruction: DestructionUiModel,
        val specializations: List<String>,
    ) : ResponseTypeUiModel()

    data class Resource(
        val resources: List<ResourceUiModel>,
    ) : ResponseTypeUiModel()
}