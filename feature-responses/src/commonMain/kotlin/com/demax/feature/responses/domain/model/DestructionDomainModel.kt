package com.demax.feature.responses.domain.model

import kotlinx.datetime.LocalDate

data class DestructionDomainModel(
    val id: String,
    val imageUrl: String?,
    val destructionDate: LocalDate,
    val address: String,
)