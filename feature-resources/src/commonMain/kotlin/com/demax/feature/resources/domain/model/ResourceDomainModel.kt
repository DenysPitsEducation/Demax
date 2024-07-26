package com.demax.feature.resources.domain.model

data class ResourceDomainModel(
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val category: String,
    val progress: Double,
    val status: StatusDomainModel,
) {

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
