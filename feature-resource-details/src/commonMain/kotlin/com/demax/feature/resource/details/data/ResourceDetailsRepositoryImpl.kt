package com.demax.feature.resource.details.data

import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.domain.model.AmountDomainModel
import com.demax.feature.resource.details.domain.model.DestructionDomainModel
import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel
import kotlinx.datetime.LocalDate

class ResourceDetailsRepositoryImpl : ResourceDetailsRepository {
    override suspend fun getDestructionDetails(): Result<ResourceDetailsDomainModel> {
        return Result.success(
            ResourceDetailsDomainModel(
                id = 1,
                imageUrl = "https://picsum.photos/1200/800",
                status = ResourceDetailsDomainModel.StatusDomainModel.ACTIVE,
                name = "Антисептичні серветки",
                category = "Медичні засоби",
                amount = AmountDomainModel(
                    totalAmount = 10,
                    currentAmount = 2,
                ),
                description = "Чудові антисептичні серветки",
                destruction = DestructionDomainModel(
                    imageUrl = "https://picsum.photos/100/100",
                    destructionDate = LocalDate(2024, 6, 13),
                    address = "вул Чорновола, 28",
                ),
            )
        )
    }
}