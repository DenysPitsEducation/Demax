package com.demax.feature.destructions.data

import com.demax.feature.destructions.domain.DestructionsRepository
import com.demax.feature.destructions.domain.model.DestructionDomainModel
import kotlinx.datetime.LocalDate

class DestructionsRepositoryImpl : DestructionsRepository {
    override suspend fun getDestructions(): Result<List<DestructionDomainModel>> {
        return Result.success(listOf(
            DestructionDomainModel(
                id = 4373,
                imageUrl = "https://picsum.photos/200/200",
                buildingType = "житловий будинок",
                address = "вул Чорновола, 28",
                destructionDate = LocalDate(year = 2024, monthNumber = 7, dayOfMonth = 8),
                resourceProgress = 0.3,
                volunteerProgress = 1.0,
                specializations = listOf("Психотерапія", "Педіатрія"),
                status = DestructionDomainModel.StatusDomainModel.ACTIVE
            ),
            DestructionDomainModel(
                id = 8246,
                imageUrl = "https://picsum.photos/300/200",
                buildingType = "медичний заклад",
                address = "вул Лобановського, 28",
                destructionDate = LocalDate(year = 2022, monthNumber = 7, dayOfMonth = 8),
                resourceProgress = 1.0,
                volunteerProgress = 0.3,
                specializations = listOf("Психотерапія", "Педіатрія"),
                status = DestructionDomainModel.StatusDomainModel.COMPLETED
            ),
        ))
    }
}