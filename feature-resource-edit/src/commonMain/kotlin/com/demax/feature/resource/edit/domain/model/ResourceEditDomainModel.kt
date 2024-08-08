package com.demax.feature.resource.edit.domain.model

data class ResourceEditDomainModel(
    val id: Long,
    val imageUrl: String?,
    val status: StatusDomainModel,
    val name: String,
    val category: String,
    val amount: Int,
    val description: String,
    val destruction: DestructionDomainModel,
) {

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
