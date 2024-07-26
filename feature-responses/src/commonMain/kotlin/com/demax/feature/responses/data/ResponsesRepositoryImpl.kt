package com.demax.feature.responses.data

import com.demax.feature.responses.domain.ResponsesRepository
import com.demax.feature.responses.domain.model.DestructionDomainModel
import com.demax.feature.responses.domain.model.ProfileDomainModel
import com.demax.feature.responses.domain.model.ResourceDomainModel
import com.demax.feature.responses.domain.model.ResponseDomainModel
import com.demax.feature.responses.domain.model.ResponseTypeDomainModel
import kotlinx.datetime.LocalDate

class ResponsesRepositoryImpl : ResponsesRepository {
    override suspend fun getResources(): Result<List<ResponseDomainModel>> {
        return Result.success(
            listOf(
                ResponseDomainModel(
                    id = 0,
                    profile = ProfileDomainModel(
                        id = 0,
                        name = "Ксюша",
                        imageUrl = "https://picsum.photos/200?random=1",
                    ),
                    type = ResponseTypeDomainModel.Volunteer(
                        destruction = DestructionDomainModel(
                            imageUrl = "https://picsum.photos/200?random=2",
                            destructionDate = LocalDate(2024, 6, 13),
                            address = "вул Чорновола, 28"
                        ),
                        specializations = listOf("Водій", "Хороша людина", "Психолог")
                    ),
                    status = ResponseDomainModel.StatusDomainModel.IDLE,
                ),
                ResponseDomainModel(
                    id = 1,
                    profile = ProfileDomainModel(
                        id = 1,
                        name = "Маша",
                        imageUrl = "https://picsum.photos/200?random=3",
                    ),
                    type = ResponseTypeDomainModel.Resource(
                        resource = ResourceDomainModel(
                            id = 0,
                            imageUrl = "https://picsum.photos/200?random=4",
                            category = "Медичні засоби",
                            name = "Антисептичні серветки",
                            quantity = 5,
                        ),
                    ),
                    status = ResponseDomainModel.StatusDomainModel.APPROVED,
                ),
            )
        )
    }
}