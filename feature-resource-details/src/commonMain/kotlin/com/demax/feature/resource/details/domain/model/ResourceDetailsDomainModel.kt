package com.demax.feature.resource.details.domain.model

data class ResourceDetailsDomainModel(
    val id: Long,
    val imageUrl: String?,
    val status: StatusDomainModel,
    val name: String,
    val category: String,
    val amount: AmountDomainModel,
    val description: String,
    val destruction: DestructionDomainModel,
) {

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
