package com.demax.feature.help.history.domain.model

import kotlinx.datetime.LocalDate

data class DestructionDomainModel(
    val id: String,
    val imageUrl: String?,
    val destructionDate: LocalDate,
    val address: String,
)