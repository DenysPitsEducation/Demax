package com.demax.feature.help.history.domain.model

sealed class HelpTypeDomainModel {
    data class Volunteer(
        val destruction: DestructionDomainModel,
        val specializations: List<String>,
    ) : HelpTypeDomainModel()

    data class Resource(
        val resources: List<ResourceDomainModel>,
    ) : HelpTypeDomainModel()
}