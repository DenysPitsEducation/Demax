package com.demax.feature.destructions.data

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDataModel
import com.demax.core.domain.model.StatusDomainModel
import com.demax.feature.destructions.domain.DestructionsRepository
import com.demax.feature.destructions.domain.model.DestructionDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

internal class DestructionsRepositoryImpl(
    private val buildingTypeMapper: BuildingTypeDomainMapper,
) : DestructionsRepository {
    override suspend fun getDestructions(): Result<List<DestructionDomainModel>> {
        return runCatching {
            val collection = Firebase.firestore.collection("destructions")
            val destructionDataModels = collection.get().documents.map { document ->
                document.id to document.data(DestructionDataModel.serializer())
            }

            val resourceIds = destructionDataModels.flatMap { it.second.resourceNeeds }
            val resources = Firebase.firestore.collection("resources")
                .where { FieldPath.documentId inArray resourceIds }
                .get().documents.associate { document ->
                    document.id to document.data(ResourceDataModel.serializer())
                }

            destructionDataModels.map { (id, model) ->
                model.toDomainModel(
                    id = id,
                    resources = model.resourceNeeds.map { resources.getValue(it) },
                )
            }
        }
    }

    private fun DestructionDataModel.toDomainModel(
        id: String,
        resources: List<ResourceDataModel>
    ): DestructionDomainModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        val resourceProgress = run {
            val currentSum = resources.sumOf { it.amount.currentAmount }
            val totalSum = resources.sumOf { it.amount.totalAmount }
            currentSum * 1.0 / totalSum
        }
        val volunteerProgress = run {
            val currentSum = volunteerNeeds.sumOf { it.amount.currentAmount }
            val totalSum = volunteerNeeds.sumOf { it.amount.totalAmount }
            currentSum * 1.0 / totalSum
        }
        return DestructionDomainModel(
            id = id,
            imageUrl = imageUrl,
            buildingType = buildingTypeMapper.mapToDomainModel(buildingType),
            address = address,
            destructionDate = formatter.parse(destructionDate),
            resourceProgress = resourceProgress,
            volunteerProgress = volunteerProgress,
            specializations = volunteerNeeds.map { it.name },
            status = if (resourceProgress < 1 || volunteerProgress < 1) StatusDomainModel.ACTIVE else StatusDomainModel.COMPLETED,
            priority = priority,
        )
    }
}