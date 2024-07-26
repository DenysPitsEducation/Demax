package com.demax.feature.resources.domain.model

data class ResourceDomainModel(
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val category: String,
    val amount: AmountDomainModel,
    val status: StatusDomainModel,
) {
    data class AmountDomainModel(
        val currentAmount: Int,
        val totalAmount: Int,
    )

    enum class StatusDomainModel {
        ACTIVE, COMPLETED
    }
}
