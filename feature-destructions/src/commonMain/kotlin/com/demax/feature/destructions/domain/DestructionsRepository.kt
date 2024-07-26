package com.demax.feature.destructions.domain

import com.demax.feature.destructions.domain.model.DestructionDomainModel

interface DestructionsRepository {
    suspend fun getDestructions(): Result<List<DestructionDomainModel>>
}