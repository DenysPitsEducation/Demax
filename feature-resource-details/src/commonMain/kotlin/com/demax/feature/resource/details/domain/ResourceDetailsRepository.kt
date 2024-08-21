package com.demax.feature.resource.details.domain

import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel

interface ResourceDetailsRepository {
    suspend fun getResourceDetails(id: String): Result<ResourceDetailsDomainModel>
}