package com.demax.feature.destruction.details.data

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.DestructionStatisticsDataModel
import com.demax.core.data.model.ResourceDataModel
import com.demax.core.data.model.ResponseDataModel
import com.demax.core.data.model.VolunteerNeedDataModel
import com.demax.core.domain.model.StatusDomainModel
import com.demax.feature.destruction.details.domain.DestructionDetailsRepository
import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionStatisticsDomainModel
import com.demax.feature.destruction.details.domain.model.NeedDomainModel
import com.demax.feature.destruction.details.domain.model.ResourceHelpBottomSheetDomainModel
import com.demax.feature.destruction.details.domain.model.VolunteerHelpBottomSheetDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.where
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class DestructionDetailsRepositoryImpl(
    private val buildingTypeMapper: BuildingTypeDomainMapper,
) : DestructionDetailsRepository {
    override suspend fun getDestructionDetails(id: String): Result<DestructionDetailsDomainModel> {
        return runCatching {
            val destructionsCollection = Firebase.firestore.collection("destructions")
            val destructionDocument = destructionsCollection.document(id).get()
            val destructionDataModel = destructionDocument.data(DestructionDataModel.serializer())
            val resourcesCollection = Firebase.firestore.collection("resources")
            val resourceDocuments = resourcesCollection.where {
                FieldPath.documentId inArray destructionDataModel.resourceNeeds
            }.get().documents
            val resources = resourceDocuments.map { document ->
                document.id to document.data(ResourceDataModel.serializer())
            }
            destructionDataModel.toDomainModel(id, resources)
        }
    }

    private fun DestructionDataModel.toDomainModel(
        id: String,
        resources: List<Pair<String, ResourceDataModel>>,
    ): DestructionDetailsDomainModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        val volunteerNeeds = volunteerNeeds.map { it.toDomainModel() }
        val resourceNeeds = resources.map { (id, resource) ->
            NeedDomainModel(
                id = id,
                name = resource.name,
                amount = AmountDomainModel(
                    currentAmount = resource.amount.currentAmount,
                    totalAmount = resource.amount.totalAmount,
                ),
            )
        }
        return DestructionDetailsDomainModel(
            id = id,
            imageUrl = imageUrl,
            status = getStatus(volunteerNeeds, resourceNeeds),
            buildingType = buildingTypeMapper.mapToDomainModel(buildingType),
            address = address,
            destructionStatistics = destructionStatistics.toDomainModel(),
            destructionDate = formatter.parse(destructionDate),
            description = description,
            volunteerNeeds = volunteerNeeds,
            resourceNeeds = resourceNeeds,
        )
    }

    private fun getStatus(
        volunteerNeeds: List<NeedDomainModel>,
        resourceNeeds: List<NeedDomainModel>
    ): StatusDomainModel {
        return if ((volunteerNeeds + resourceNeeds).any { it.amount.currentAmount < it.amount.totalAmount }) {
            StatusDomainModel.ACTIVE
        } else {
            StatusDomainModel.COMPLETED
        }
    }

    private fun DestructionStatisticsDataModel.toDomainModel(): DestructionStatisticsDomainModel {
        return DestructionStatisticsDomainModel(
            destroyedFloors = destroyedFloors,
            destroyedSections = destroyedSections
        )
    }

    private fun VolunteerNeedDataModel.toDomainModel(): NeedDomainModel {
        return NeedDomainModel(
            id = name,
            name = name,
            amount = AmountDomainModel(
                currentAmount = amount.currentAmount,
                totalAmount = amount.totalAmount,
            ),
        )
    }

    override suspend fun sendVolunteerResponse(
        destructionId: String,
        volunteerHelpBottomSheet: VolunteerHelpBottomSheetDomainModel
    ): Result<Unit> {
        return runCatching {
            val resourcesCollection = Firebase.firestore.collection("responses")
            val resourceDocument = resourcesCollection.document
            resourceDocument.set(
                ResponseDataModel(
                    profile = ResponseDataModel.Profile("1", "Denys", null),
                    status = "idle",
                    type = "volunteer",
                    destructionId = destructionId,
                    specializations = volunteerHelpBottomSheet.needs
                        .filter { it.isSelected }
                        .map { it.title },
                    resources = null,
                )
            )
        }
    }

    override suspend fun sendResourceResponse(
        destructionId: String,
        resourceHelpBottomSheet: ResourceHelpBottomSheetDomainModel
    ): Result<Unit> {
        return runCatching {
            val resourcesCollection = Firebase.firestore.collection("responses")
            val resourceDocument = resourcesCollection.document
            resourceDocument.set(
                ResponseDataModel(
                    profile = ResponseDataModel.Profile("1", "Denys", null),
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