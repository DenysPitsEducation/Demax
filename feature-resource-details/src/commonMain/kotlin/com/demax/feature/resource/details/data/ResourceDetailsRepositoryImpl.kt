package com.demax.feature.resource.details.data

import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDetailsDataModel
import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.domain.model.AmountDomainModel
import com.demax.feature.resource.details.domain.model.DestructionDomainModel
import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class ResourceDetailsRepositoryImpl(
    private val categoryMapper: ResourceCategoryDomainMapper,
) : ResourceDetailsRepository {
    override suspend fun getResourceDetails(id: String): Result<ResourceDetailsDomainModel> {
        return runCatching {
            val database = Firebase.firestore

            val resourceDetailsCollection = database.collection("resource_details")
            val resourceDocument = resourceDetailsCollection.document(id).get()
            val resourceDataModel = resourceDocument.data(ResourceDetailsDataModel.serializer())

            val destructionsCollection = database.collection("destructions")
            val destructionDocument = destructionsCollection.document(resourceDataModel.destructionId).get()
            val destructionDataModel = destructionDocument.data(DestructionDataModel.serializer())

            resourceDataModel.toDomainModel(id, destructionDataModel)
        }
    }

    private fun ResourceDetailsDataModel.toDomainModel(resourceId: String, destruction: DestructionDataModel): ResourceDetailsDomainModel {
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
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return DestructionDomainModel(
            imageUrl = imageUrl,
            destructionDate = formatter.parse(destructionDate),
            address = address
        )
    }
}