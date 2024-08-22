package com.demax.feature.resource.edit.data

import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.AmountDataModel
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDataModel
import com.demax.core.data.model.ResourceDetailsDataModel
import com.demax.core.utils.UuidGenerator
import com.demax.feature.resource.edit.domain.ResourceEditRepository
import com.demax.feature.resource.edit.domain.model.DestructionDomainModel
import com.demax.feature.resource.edit.domain.model.ResourceEditDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage

class ResourceEditRepositoryImpl(
    private val categoryMapper: ResourceCategoryDomainMapper,
) : ResourceEditRepository {
    override suspend fun getResourceEditModel(resourceId: String?): Result<ResourceEditDomainModel> {
        return runCatching {
            val resourceDataModel = resourceId?.let { getResourceDataModel(it) }

            val destructionsCollection = Firebase.firestore.collection("destructions")
            val availableDestructions = destructionsCollection.get().documents.map { document ->
                val productDataModel = document.data(DestructionDataModel.serializer())
                productDataModel.toDomainModel(document.id)
            }

            ResourceEditDomainModel(
                id = resourceId ?: UuidGenerator.generate(),
                imageUrl = resourceDataModel?.imageUrl,
                imageFile = null,
                name = resourceDataModel?.name,
                category = resourceDataModel?.category?.let { categoryMapper.mapToDomainModel(it) },
                totalAmount = resourceDataModel?.amount?.totalAmount,
                currentAmount = resourceDataModel?.amount?.currentAmount,
                description = resourceDataModel?.description,
                availableDestructions = availableDestructions,
                selectedDestruction = availableDestructions.firstOrNull { it.id == resourceId }
            )
        }
    }

    override suspend fun saveResource(resource: ResourceEditDomainModel): Result<Unit> {
        return runCatching {
            val uploadedImageUrl = if (resource.imageFile != null) {
                uploadImageToStorage(resource.imageFile, resource.id)
            } else null
            val resourceDataModel = resource.toResourceDataModel(uploadedImageUrl)
            val resourceDetailsDataModel = resource.toResourceDetailsDataModel(uploadedImageUrl)
            saveResourceInFirestore(resource.id, resourceDataModel, resourceDetailsDataModel)
        }
    }

    private suspend fun getResourceDataModel(resourceId: String): ResourceDetailsDataModel {
        val resourceDetailsCollection = Firebase.firestore.collection("resource_details")
        val resourceDocument = resourceDetailsCollection.document(resourceId).get()
        return resourceDocument.data(ResourceDetailsDataModel.serializer())
    }

    private fun DestructionDataModel.toDomainModel(id: String): DestructionDomainModel {
        return DestructionDomainModel(
            id = id,
            address = address
        )
    }

    private fun ResourceEditDomainModel.toResourceDataModel(uploadedImageUrl: String?): ResourceDataModel {
        return ResourceDataModel(
            imageUrl = uploadedImageUrl ?: imageUrl,
            name = name ?: throw ValidationException,
            category = categoryMapper.mapToDataModel(category ?: throw ValidationException),
            amount = AmountDataModel(
                currentAmount = currentAmount ?: throw ValidationException,
                totalAmount = totalAmount ?: throw ValidationException,
            ),
        )
    }

    private fun ResourceEditDomainModel.toResourceDetailsDataModel(uploadedImageUrl: String?): ResourceDetailsDataModel {
        return ResourceDetailsDataModel(
            imageUrl = uploadedImageUrl ?: imageUrl,
            name = name ?: throw ValidationException,
            category = categoryMapper.mapToDataModel(category ?: throw ValidationException),
            amount = AmountDataModel(
                currentAmount = currentAmount ?: throw ValidationException,
                totalAmount = totalAmount ?: throw ValidationException,
            ),
            description = description ?: throw ValidationException,
            destructionId = selectedDestruction?.id ?: throw ValidationException,
        )
    }

    private suspend fun uploadImageToStorage(image: File, resourceId: String): String {
        val storageRef = Firebase.storage.reference
        val imagesStorageRef = storageRef.child("resources/${resourceId}")
        val imageStorageRef = imagesStorageRef.child("0.jpg")
        imageStorageRef.putFile(image)
        return imageStorageRef.getDownloadUrl()
    }

    private suspend fun saveResourceInFirestore(
        resourceId: String,
        resourceDataModel: ResourceDataModel,
        resourceDetailsDataModel: ResourceDetailsDataModel
    ) {
        val database = Firebase.firestore
        val resourcesCollection = database.collection("resources")
        val resourceDetailsCollection = database.collection("resource_details")
        val resourceDocument = resourcesCollection.document(resourceId)
        val resourceDetailsDocument = resourceDetailsCollection.document(resourceId)
        database.batch().apply {
            set(resourceDocument, resourceDataModel)
            set(resourceDetailsDocument, resourceDetailsDataModel)
        }.commit()

        // TODO Pits: update destructions
        /*val resourcesOfDestructionDocuments = resourceDetailsCollection.where {
            any("destructionId" equalTo resourceDetailsDataModel.destructionId)
        }.get().documents
        resourcesOfDestructionDocuments.forEach {

        }*/
    }
}