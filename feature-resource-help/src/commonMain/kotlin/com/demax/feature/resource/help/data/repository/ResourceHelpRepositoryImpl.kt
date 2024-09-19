package com.demax.feature.resource.help.data.repository

import com.demax.core.data.model.ResponseDataModel
import com.demax.feature.resource.help.domain.model.ResourceHelpBottomSheetDomainModel
import com.demax.feature.resource.help.domain.repository.ResourceHelpRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class ResourceHelpRepositoryImpl : ResourceHelpRepository {
    override suspend fun sendResourceResponse(
        resourceHelpBottomSheet: ResourceHelpBottomSheetDomainModel
    ): Result<Unit> {
        return runCatching {
            val resourcesCollection = Firebase.firestore.collection("responses")
            val resourceDocument = resourcesCollection.document
            resourceDocument.set(
                ResponseDataModel(
                    profileId = Firebase.auth.currentUser?.uid ?: throw Exception("User not found"),
                    status = "idle",
                    type = "resource",
                    destructionId = null,
                    specializations = null,
                    resources = resourceHelpBottomSheet.needs.filter { it.isSelected }.map { resource ->
                        ResponseDataModel.Resource(
                            id = resource.id,
                            quantity = resource.quantityText.toInt(),
                        )
                    },
                )
            )
        }
    }
}