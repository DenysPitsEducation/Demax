package com.demax.feature.destruction.edit.domain

import com.demax.feature.destruction.edit.domain.model.DestructionEditDomainModel

interface DestructionEditRepository {
    suspend fun getDestructionEditModel(destructionId: String?): Result<DestructionEditDomainModel>
    suspend fun saveDestruction(destruction: DestructionEditDomainModel): Result<Unit>
}