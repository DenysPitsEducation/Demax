package com.demax.feature.resources.data

import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.feature.resources.domain.model.ResourceDomainModel
import kotlinx.datetime.LocalDate

class ResourcesRepositoryImpl : ResourcesRepository {
    override suspend fun getResources(): Result<List<ResourceDomainModel>> {
        return Result.success(listOf(
            ResourceDomainModel(
                id = 4373,
                imageUrl = "https://picsum.photos/300/200?random=1",
                name = "Антисептичні серветки",
                category = "Медичні засоби",
                amount = ResourceDomainModel.AmountDomainModel(currentAmount = 3, totalAmount = 10),
                status = ResourceDomainModel.StatusDomainModel.ACTIVE
            ),
            ResourceDomainModel(
                id = 8246,
                imageUrl = "https://picsum.photos/300/200?random=2",
                name = "Цегла",
                category = "Будівельні матеріали",
                amount = ResourceDomainModel.AmountDomainModel(currentAmount = 5, totalAmount = 5),
                status = ResourceDomainModel.StatusDomainModel.COMPLETED
            ),
        ))
    }
}