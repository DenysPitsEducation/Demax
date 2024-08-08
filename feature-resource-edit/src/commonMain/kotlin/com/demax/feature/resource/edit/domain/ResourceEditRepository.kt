package com.demax.feature.resource.edit.domain

import com.demax.feature.resource.edit.domain.model.ResourceEditDomainModel

interface ResourceEditRepository {
    suspend fun getDestructionDetails(): Result<ResourceEditDomainModel>
}