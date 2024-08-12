package com.demax.feature.destructions.data

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.mapper.StatusDomainMapper
import com.demax.feature.destructions.data.model.DestructionDataModel
import com.demax.feature.destructions.domain.DestructionsRepository
import com.demax.feature.destructions.domain.model.DestructionDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.startAfter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

internal class DestructionsRepositoryImpl(
    private val buildingTypeMapper: BuildingTypeDomainMapper,
    private val statusMapper: StatusDomainMapper,
) : DestructionsRepository {
    override suspend fun getDestructions(): Result<List<DestructionDomainModel>> {
        return runCatching {
            val collection = Firebase.firestore.collection("destructions")
            collection.get().documents.map { document ->
                val productDataModel = document.data(DestructionDataModel.serializer())
                productDataModel.toDomainModel(document.id)
            }
        }
    }

    private fun DestructionDataModel.toDomainModel(id: String): DestructionDomainModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return DestructionDomainModel(
            id = id,
            imageUrl = imageUrl,
            buildingType = buildingTypeMapper.mapToDomainModel(buildingType),
            address = address,
            destructionDate = formatter.parse(destructionDate),
            resourceProgress = resourceProgress,
            volunteerProgress = volunteerProgress,
            specializations = specializations,
            status = statusMapper.mapToDomainModel(status),
            priority = priority,
        )
    }
}