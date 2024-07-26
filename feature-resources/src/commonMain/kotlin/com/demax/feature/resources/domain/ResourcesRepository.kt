package com.demax.feature.resources.domain

import com.demax.feature.resources.domain.model.ResourceDomainModel

interface ResourcesRepository {
    suspend fun getResources(): Result<List<ResourceDomainModel>>
}