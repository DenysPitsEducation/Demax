package com.demax.feature.resources.data

import com.demax.core.data.model.ResourceDataModel
import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.domain.model.StatusDomainModel
import com.demax.feature.resources.domain.model.ResourceDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class ResourcesRepositoryImpl(
    private val resourceCategoryMapper: ResourceCategoryDomainMapper,
) : ResourcesRepository {
    override suspend fun getResources(): Result<List<ResourceDomainModel>> {
        return runCatching {
            val collection = Firebase.firestore.collection("resources")
            collection.get().documents.map { document ->
                val resourceDataModel = document.data(ResourceDataModel.serializer())
                resourceDataModel.toDomainModel(document.id)
            }
        }
    }
    private fun ResourceDataModel.toDomainModel(id: String): ResourceDomainModel {
        return ResourceDomainModel(
            id = id,
            imageUrl = imageUrl,
            name = name,
            category = resourceCategoryMapper.mapToDomainModel(category),
            amount = ResourceDomainModel.AmountDomainModel(
                currentAmount = amount.currentAmount,
                totalAmount = amount.totalAmount,
            ),
            status = if (amount.currentAmount >= amount.totalAmount) {
                StatusDomainModel.COMPLETED
            } else {
                StatusDomainModel.ACTIVE
            }
        )
    }
}