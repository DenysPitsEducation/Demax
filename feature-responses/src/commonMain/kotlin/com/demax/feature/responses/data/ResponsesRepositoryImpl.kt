package com.demax.feature.responses.data

import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.AmountDataModel
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDataModel
import com.demax.core.data.model.ResponseDataModel
import com.demax.core.data.model.VolunteerNeedDataModel
import com.demax.feature.responses.domain.ResponsesRepository
import com.demax.feature.responses.domain.model.DestructionDomainModel
import com.demax.feature.responses.domain.model.ProfileDomainModel
import com.demax.feature.responses.domain.model.ResourceDomainModel
import com.demax.feature.responses.domain.model.ResponseDomainModel
import com.demax.feature.responses.domain.model.ResponseTypeDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class ResponsesRepositoryImpl(
    private val resourceCategoryMapper: ResourceCategoryDomainMapper,
) : ResponsesRepository {
    override suspend fun getResponses(): Result<List<ResponseDomainModel>> {
        return runCatching {
            val database = Firebase.firestore
            val responsesCollection = database.collection("responses")
            val responseDataModels = responsesCollection.get().documents.map { document ->
                document.id to document.data(ResponseDataModel.serializer())
            }

            val destructionIds =
                responseDataModels.flatMap { listOfNotNull(it.second.destructionId) }
            val destructions = if (destructionIds.isNotEmpty()) {
                database.collection("destructions")
                    .where { FieldPath.documentId inArray destructionIds }
                    .get().documents.associate { document ->
                        document.id to document.data(DestructionDataModel.serializer())
                    }
            } else emptyMap()

            val resourceIds = responseDataModels.flatMap { (_, response) ->
                response.resources?.map { it.id }.orEmpty()
            }
            val resources = if (resourceIds.isNotEmpty()) {
                database.collection("resources")
                    .where { FieldPath.documentId inArray resourceIds }
                    .get().documents.associate { document ->
                        document.id to document.data(ResourceDataModel.serializer())
                    }
            } else emptyMap()

            responseDataModels.map { (id, response) ->
                response.toDomainModel(id, destructions, resources)
            }
        }
    }

    private fun ResponseDataModel.toDomainModel(
        id: String,
        destructions: Map<String, DestructionDataModel>,
        resources: Map<String, ResourceDataModel>
    ): ResponseDomainModel {
        return ResponseDomainModel(
            id = id,
            profile = ProfileDomainModel(
                id = profile.id,
                name = profile.name,
                imageUrl = profile.imageUrl,
            ),
            status = when (status) {
                "approved" -> ResponseDomainModel.StatusDomainModel.APPROVED
                "rejected" -> ResponseDomainModel.StatusDomainModel.REJECTED
                else -> ResponseDomainModel.StatusDomainModel.IDLE
            },
            type = if (type == "resource") {
                ResponseTypeDomainModel.Resource(
                    resources = this.resources!!.map { resource ->
                        val resourceDataModel = resources.getValue(resource.id)
                        ResourceDomainModel(
                            id = resource.id,
                            imageUrl = resourceDataModel.imageUrl,
                            category = resourceCategoryMapper.mapToDomainModel(resourceDataModel.category),
                            name = resourceDataModel.name,
                            quantity = resource.quantity,
                        )
                    }
                )
            } else {
                val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
                val destruction = destructions.getValue(destructionId!!)
                ResponseTypeDomainModel.Volunteer(
                    destruction = DestructionDomainModel(
                        id = destructionId!!,
                        imageUrl = destruction.imageUrl,
                        destructionDate = formatter.parse(destruction.destructionDate),
                        address = destruction.address,
                    ),
                    specializations = specializations.orEmpty(),
                )
            }
        )
    }

    override suspend fun approveResponse(response: ResponseDomainModel): Result<Unit> {
        return runCatching {
            val responsesCollection = Firebase.firestore.collection("responses")
            when (response.type) {
                is ResponseTypeDomainModel.Resource -> {
                    val resourcesCollection = Firebase.firestore.collection("resources")
                    Firebase.firestore.batch().apply {
                        response.type.resources.forEach { resource ->
                            update(resourcesCollection.document(resource.id), "amount.currentAmount" to FieldValue.increment(resource.quantity))
                        }
                        update(responsesCollection.document(response.id), "status" to "approved")
                    }.commit()
                }
                is ResponseTypeDomainModel.Volunteer -> {
                    val destructionsCollection = Firebase.firestore.collection("destructions")
                    val destructionDocument = destructionsCollection.document(response.type.destruction.id)
                    val destructionDataModel = destructionDocument.get().data(DestructionDataModel.serializer())
                    val updatedVolunteerNeeds = destructionDataModel.volunteerNeeds.map { volunteerNeed ->
                        if (volunteerNeed.name in response.type.specializations) {
                            volunteerNeed.copy(
                                amount = volunteerNeed.amount.copy(
                                    currentAmount = volunteerNeed.amount.currentAmount + 1,
                                )
                            )
                        } else volunteerNeed
                    }
                    Firebase.firestore.batch().apply {
                        update(destructionDocument, "volunteerNeeds" to updatedVolunteerNeeds)
                        update(responsesCollection.document(response.id), "status" to "approved")
                    }.commit()
                }
            }
        }
    }

    override suspend fun rejectResponse(response: ResponseDomainModel): Result<Unit> {
        return runCatching {
            val responsesCollection = Firebase.firestore.collection("responses")
            responsesCollection.document(response.id).update("status" to "rejected")
        }
    }
}