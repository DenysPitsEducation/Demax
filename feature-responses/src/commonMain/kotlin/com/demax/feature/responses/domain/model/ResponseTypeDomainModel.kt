package com.demax.feature.responses.domain.model

sealed class ResponseTypeDomainModel {
    data class Volunteer(
        val destruction: DestructionDomainModel,
        val specializations: List<String>,
    ) : ResponseTypeDomainModel()

    data class Resource(
        val resources: List<ResourceDomainModel>,
    ) : ResponseTypeDomainModel()
}