package com.demax.feature.destruction.details.mvi

import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel

data class DestructionDetailsState(
    val destructionDetails: DestructionDetailsDomainModel?,
    val isAdministrator: Boolean,
)
