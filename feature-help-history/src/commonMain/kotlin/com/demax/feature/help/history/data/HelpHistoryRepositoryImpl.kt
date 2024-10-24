package com.demax.feature.help.history.data

import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.ResourceDataModel
import com.demax.core.data.model.ResponseDataModel
import com.demax.feature.help.history.domain.HelpHistoryRepository
import com.demax.feature.help.history.domain.model.DestructionDomainModel
import com.demax.feature.help.history.domain.model.ResourceDomainModel
import com.demax.feature.help.history.domain.model.HelpDomainModel
import com.demax.feature.help.history.domain.model.HelpTypeDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.byUnicodePattern

class HelpHistoryRepositoryImpl(
    private val resourceCategoryMapper: ResourceCategoryDomainMapper,
) : HelpHistoryRepository {
    override suspend fun getHelpHistory(profileId: String): Result<List<HelpDomainModel>> {
        return runCatching {
            val database = Firebase.firestore
            val responsesCollection = database.collection("responses")
            val responseDataModels = responsesCollection.where {
                all("profileId" equalTo profileId)
            }.get().documents.map { document ->
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
    ): HelpDomainModel {
        return HelpDomainModel(
            id = id,
            status = when (status) {
                "approved" -> HelpDomainModel.StatusDomainModel.APPROVED
                "rejected" -> HelpDomainModel.StatusDomainModel.REJECTED
                else -> HelpDomainModel.StatusDomainModel.IDLE
            },
            type = if (type == "resource") {
                HelpTypeDomainModel.Resource(
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
                val formatter = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm") }
                val destruction = destructions.getValue(destructionId!!)
                HelpTypeDomainModel.Volunteer(
                    destruction = DestructionDomainModel(
                        id = destructionId!!,
                        imageUrl = destruction.imageUrl,
                        destructionDate = formatter.parse(destruction.destructionDate).date,
                        address = destruction.address,
                    ),
                    specializations = specializations.orEmpty(),
                )
            }
        )
    }
}