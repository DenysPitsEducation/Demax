package com.demax.feature.destruction.details.data

import com.demax.feature.destruction.details.domain.DestructionDetailsRepository
import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionStatisticsDomainModel
import com.demax.feature.destruction.details.domain.model.NeedDomainModel
import kotlinx.datetime.LocalDate

class DestructionDetailsRepositoryImpl : DestructionDetailsRepository {
    override suspend fun getDestructionDetails(): Result<DestructionDetailsDomainModel> {
        return Result.success(
            DestructionDetailsDomainModel(
                id = "1",
                imageUrl = "https://picsum.photos/1200/800",
                status = DestructionDetailsDomainModel.StatusDomainModel.ACTIVE,
                buildingType = "Житловий будинок",
                address = "вул Чорновола, 28",
                destructionStatistics = DestructionStatisticsDomainModel(
                    destroyedFloors = "10",
                    destroyedSections = "1"
                ),
                destructionDate = LocalDate(2024, 7, 8),
                description = "Будівля зазнала невиправних руйнувань, приблизна кількість жертв становить ...",
                volunteerNeeds = listOf(
                    NeedDomainModel(name = "Психотерапія", amount = AmountDomainModel(3, 10)),
                    NeedDomainModel(name = "Педіатрія", amount = AmountDomainModel(1, 2)),
                ),
                resourceNeeds = listOf(
                    NeedDomainModel(name = "Зарядні пристрої", amount = AmountDomainModel(8, 8)),
                ),
            )
        )
    }
}