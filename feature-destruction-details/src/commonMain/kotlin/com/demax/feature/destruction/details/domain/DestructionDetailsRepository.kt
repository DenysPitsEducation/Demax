package com.demax.feature.destruction.details.domain

import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel

interface DestructionDetailsRepository {
    suspend fun getDestructionDetails(): Result<DestructionDetailsDomainModel>
}