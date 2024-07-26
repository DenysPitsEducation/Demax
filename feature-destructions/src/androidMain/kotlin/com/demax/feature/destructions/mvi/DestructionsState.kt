package com.demax.feature.destructions.mvi

import com.demax.feature.destructions.domain.model.DestructionDomainModel

data class DestructionsState(
    val searchInput: String,
    val isAdministrator: Boolean,
    val destructions: List<DestructionDomainModel>,
)
