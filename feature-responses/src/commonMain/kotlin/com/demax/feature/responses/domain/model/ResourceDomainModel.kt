package com.demax.feature.responses.domain.model

data class ResourceDomainModel(
    val id: Long,
    val imageUrl: String?,
    val category: String,
    val name: String,
    val quantity: Int,
)