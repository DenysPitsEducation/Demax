package com.demax.feature.resource.edit.data

import com.demax.feature.resource.edit.domain.ResourceEditRepository
import com.demax.feature.resource.edit.domain.model.DestructionDomainModel
import com.demax.feature.resource.edit.domain.model.ResourceEditDomainModel

class ResourceEditRepositoryImpl : ResourceEditRepository {
    override suspend fun getDestructionDetails(): Result<ResourceEditDomainModel> {
        return Result.success(
            ResourceEditDomainModel(
                id = 1,
                imageUrl = "https://picsum.photos/1200/800",
                status = ResourceEditDomainModel.StatusDomainModel.ACTIVE,
                name = "Антисептичні серветки",
                category = "Медичні засоби",
                amount = 8,
                description = "Чудові антисептичні серветки",
                destruction = DestructionDomainModel(
                    id = 1,
                    address = "вул Чорновола, 28",
                ),
            )
        )
    }
}