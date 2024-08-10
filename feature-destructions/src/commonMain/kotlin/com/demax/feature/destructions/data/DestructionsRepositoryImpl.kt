package com.demax.feature.destructions.data

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

class DestructionsRepositoryImpl : DestructionsRepository {
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
            buildingType = getBuildingTypeDomainModel(buildingType),
            address = address,
            destructionDate = formatter.parse(destructionDate),
            resourceProgress = resourceProgress,
            volunteerProgress = volunteerProgress,
            specializations = specializations,
            status = getStatusDomainModel(status),
            priority = priority,
        )
    }

    private fun getBuildingTypeDomainModel(buildingType: String): DestructionDomainModel.BuildingType {
        return when (buildingType) {
            "residential" -> DestructionDomainModel.BuildingType.RESIDENTIAL
            "medical_order" -> DestructionDomainModel.BuildingType.MEDICAL_INSTITUTION
            "educational_institution" -> DestructionDomainModel.BuildingType.EDUCATIONAL_INSTITUTION
            "shopping_center" -> DestructionDomainModel.BuildingType.SHOPPING_CENTER
            "office_building" -> DestructionDomainModel.BuildingType.OFFICE_BUILDING
            "transportation_infrastructure" -> DestructionDomainModel.BuildingType.TRANSPORTATION_INFRASTRUCTURE
            "warehouse_building" -> DestructionDomainModel.BuildingType.WAREHOUSE_BUILDING
            "parking_lot" -> DestructionDomainModel.BuildingType.PARKING_LOT
            "religious_institution" -> DestructionDomainModel.BuildingType.RELIGIOUS_INSTITUTION
            "cultural_institution" -> DestructionDomainModel.BuildingType.CULTURAL_INSTITUTION
            "catering_facility" -> DestructionDomainModel.BuildingType.CATERING_FACILITY
            "entertainment_complex" -> DestructionDomainModel.BuildingType.ENTERTAINMENT_COMPLEX
            "sports_facility" -> DestructionDomainModel.BuildingType.SPORTS_FACILITY
            else -> DestructionDomainModel.BuildingType.UNSPECIFIED
        }
    }

    private fun getStatusDomainModel(status: String): DestructionDomainModel.StatusDomainModel {
        return when (status) {
            "completed" -> DestructionDomainModel.StatusDomainModel.COMPLETED
            else -> DestructionDomainModel.StatusDomainModel.ACTIVE
        }
    }
}