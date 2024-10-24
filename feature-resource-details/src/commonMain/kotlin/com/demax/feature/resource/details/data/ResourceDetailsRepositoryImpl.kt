package com.demax.feature.resource.details.data

import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDataModel
import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.domain.model.AmountDomainModel
import com.demax.feature.resource.details.domain.model.DestructionDomainModel
import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.byUnicodePattern

class ResourceDetailsRepositoryImpl(
    private val categoryMapper: ResourceCategoryDomainMapper,
) : ResourceDetailsRepository {
    override suspend fun getResourceDetails(id: String): Result<ResourceDetailsDomainModel> {
        return runCatching {
            val database = Firebase.firestore

            val resourcesCollection = database.collection("resources")
            val resourceDocument = resourcesCollection.document(id).get()
            val resourceDataModel = resourceDocument.data(ResourceDataModel.serializer())

            val destructionsCollection = database.collection("destructions")
            val destructionDocument = destructionsCollection.document(resourceDataModel.destructionId).get()
            val destructionDataModel = destructionDocument.data(DestructionDataModel.serializer())

            resourceDataModel.toDomainModel(id, destructionDataModel)
        }
    }

    private fun ResourceDataModel.toDomainModel(resourceId: String, destruction: DestructionDataModel): ResourceDetailsDomainModel {
        return ResourceDetailsDomainModel(
            id = resourceId,
            imageUrl = imageUrl,
            name = name,
            category = categoryMapper.mapToDomainModel(category),
            amount = AmountDomainModel(
                currentAmount = amount.currentAmount,
                totalAmount = amount.totalAmount,
            ),
            description = description,
            destruction = destruction.toDomainModel()
        )
    }

    private fun DestructionDataModel.toDomainModel(): DestructionDomainModel {
        val formatter = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm") }
        return DestructionDomainModel(
            imageUrl = imageUrl,
            destructionDate = formatter.parse(destructionDate).date,
            address = address
        )
    }
}