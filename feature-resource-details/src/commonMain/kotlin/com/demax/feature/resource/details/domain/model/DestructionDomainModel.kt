package com.demax.feature.resource.details.domain.model

import kotlinx.datetime.LocalDate

data class DestructionDomainModel(
    val imageUrl: String?,
    val destructionDate: LocalDate,
    val address: String,
)
