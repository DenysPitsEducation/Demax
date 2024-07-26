package com.demax.feature.resources.data

import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.feature.resources.domain.model.ResourceDomainModel
import kotlinx.datetime.LocalDate

class ResourcesRepositoryImpl : ResourcesRepository {
    override suspend fun getResources(): Result<List<ResourceDomainModel>> {
        return Result.success(listOf(
            ResourceDomainModel(
                id = 4373,
                imageUrl = "https://picsum.photos/200/200",
                name = "Антисептичні серветки",
                category = "Медичні засоби",
                progress = 0.3,
                status = ResourceDomainModel.StatusDomainModel.ACTIVE
            ),
            ResourceDomainModel(
                id = 8246,
                imageUrl = "https://picsum.photos/300/200",
                name = "Цегла",
                category = "Будівельні матеріали",
                progress = 1.0,
                status = ResourceDomainModel.StatusDomainModel.COMPLETED
            ),
        ))
    }
}