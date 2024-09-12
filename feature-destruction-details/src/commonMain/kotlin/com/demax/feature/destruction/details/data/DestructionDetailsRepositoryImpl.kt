package com.demax.feature.destruction.details.data

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.mapper.StatusDomainMapper
import com.demax.core.data.model.DestructionDetailsDataModel
import com.demax.core.data.model.DestructionStatisticsDataModel
import com.demax.core.data.model.NeedDataModel
import com.demax.feature.destruction.details.domain.DestructionDetailsRepository
import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionStatisticsDomainModel
import com.demax.feature.destruction.details.domain.model.NeedDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class DestructionDetailsRepositoryImpl(
    private val buildingTypeMapper: BuildingTypeDomainMapper,
    private val statusMapper: StatusDomainMapper,
) : DestructionDetailsRepository {
    override suspend fun getDestructionDetails(id: String): Result<DestructionDetailsDomainModel> {
        return runCatching {
            val collection = Firebase.firestore.collection("destruction_details")
            val document = collection.document(id).get()
            val dataModel = document.data(DestructionDetailsDataModel.serializer())
            dataModel.toDomainModel(id)
        }
    }

    private fun DestructionDetailsDataModel.toDomainModel(id: String): DestructionDetailsDomainModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return DestructionDetailsDomainModel(
            id = id,
            imageUrl = imageUrl,
            status = statusMapper.mapToDomainModel(status),
            buildingType = buildingTypeMapper.mapToDomainModel(buildingType),
            address = address,
            destructionStatistics = destructionStatistics.toDomainModel(),
            destructionDate = formatter.parse(destructionDate),
            description = description,
            volunteerNeeds = volunteerNeeds.map { it.toDomainModel() },
            resourceNeeds = resourceNeeds.map { it.toDomainModel() },
        )
    }

    private fun DestructionStatisticsDataModel.toDomainModel(): DestructionStatisticsDomainModel {
        return DestructionStatisticsDomainModel(
            destroyedFloors = destroyedFloors,
            destroyedSections = destroyedSections
        )
    }

    private fun NeedDataModel.toDomainModel(): NeedDomainModel {
        return NeedDomainModel(
            name = name,
            amount = AmountDomainModel(
                currentAmount = amount.currentAmount,
                totalAmount = amount.totalAmount,
            ),
        )
    }
}