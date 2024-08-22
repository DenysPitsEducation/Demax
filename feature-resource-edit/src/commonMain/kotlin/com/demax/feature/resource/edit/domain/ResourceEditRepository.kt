package com.demax.feature.resource.edit.domain

import com.demax.feature.resource.edit.domain.model.ResourceEditDomainModel

interface ResourceEditRepository {
    suspend fun getResourceEditModel(resourceId: String?): Result<ResourceEditDomainModel>
    suspend fun saveResource(resource: ResourceEditDomainModel): Result<Unit>
}