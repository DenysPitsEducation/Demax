package com.demax.feature.destruction.edit.data

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.AmountDataModel
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.DestructionDetailsDataModel
import com.demax.core.data.model.DestructionStatisticsDataModel
import com.demax.core.data.model.NeedDataModel
import com.demax.core.utils.UuidGenerator
import com.demax.feature.destruction.edit.domain.DestructionEditRepository
import com.demax.feature.destruction.edit.domain.model.DestructionEditDomainModel
import com.demax.feature.destruction.edit.domain.model.NeedsDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class DestructionEditRepositoryImpl(
    private val categoryMapper: ResourceCategoryDomainMapper,
    private val buildingTypeMapper: BuildingTypeDomainMapper,
) : DestructionEditRepository {
    override suspend fun getDestructionEditModel(destructionId: String?): Result<DestructionEditDomainModel> {
        return runCatching {
            DestructionEditDomainModel(
                id = UuidGenerator.generate(),
                imageUrl = null,
                imageFile = null,
                buildingType = null,
                address = null,
                predictionSwitch = null,
                numberOfApartments = null,
                apartmentsSquare = null,
                destroyedFloors = null,
                destroyedSections = null,
                destructionDate = null,
                description = null,
                needsDomainModel = NeedsDomainModel(
                    helpPackages = 0,
                    specializations = getInitialSpecializations()
                )
            )
        }
    }

    private fun getInitialSpecializations(): List<NeedsDomainModel.SpecializationDomainModel> {
        val names = listOf(
            "Медичний персонал",
            "Рятувальники",
            "Будівельні спеціалісти",
            "Логістика та транспорт",
            "Технічний персонал",
            "Координація та адміністрація",
            "Кухарі та харчування",
            "Комунікація та інформація",
            "Юристи та правова допомога"
        )
        return names.map {
            NeedsDomainModel.SpecializationDomainModel(
                name = it,
                quantity = 0,
            )
        }
    }

    override suspend fun saveDestruction(destruction: DestructionEditDomainModel): Result<Unit> {
        return runCatching {
            val uploadedImageUrl = if (destruction.imageFile != null) {
                uploadImageToStorage(destruction.imageFile, destruction.id)
            } else null
            val destructionDataModel = destruction.toDestructionDataModel(uploadedImageUrl)
            val destructionDetailsDataModel =
                destruction.toDestructionDetailsDataModel(uploadedImageUrl)
            saveDestructionInFirestore(
                destruction.id,
                destructionDataModel,
                destructionDetailsDataModel
            )
        }
    }

    private fun DestructionEditDomainModel.toDestructionDataModel(uploadedImageUrl: String?): DestructionDataModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return DestructionDataModel(
            imageUrl = uploadedImageUrl ?: imageUrl,
            buildingType = buildingTypeMapper.mapToDataModel(
                buildingType ?: throw ValidationException
            ),
            address = address ?: throw ValidationException,
            destructionDate = formatter.format(destructionDate ?: throw ValidationException),
            resourceProgress = 0.0,
            volunteerProgress = 0.0,
            specializations = needsDomainModel.specializations
                .filter { it.quantity > 0 }
                .map { it.name },
            status = "active",
            priority = 10 // TODO Pits: should be some logic here
        )
    }

    private fun DestructionEditDomainModel.toDestructionDetailsDataModel(uploadedImageUrl: String?): DestructionDetailsDataModel {
        val formatter = LocalDate.Format { byUnicodePattern("yyyy-MM-dd") }
        return DestructionDetailsDataModel(
            imageUrl = uploadedImageUrl ?: imageUrl,
            status = "active",
            buildingType = buildingTypeMapper.mapToDataModel(
                buildingType ?: throw ValidationException
            ),
            address = address ?: throw ValidationException,
            destructionStatistics = DestructionStatisticsDataModel(
                destroyedFloors = destroyedFloors.toString(),
                destroyedSections = destroyedSections.toString(),
            ),
            destructionDate = formatter.format(destructionDate ?: throw ValidationException),
            description = description ?: throw ValidationException,
            volunteerNeeds = needsDomainModel.specializations
                .filter { it.quantity > 0 }
                .map { specialization ->
                    NeedDataModel(
                        name = specialization.name,
                        amount = AmountDataModel(
                            currentAmount = 0,
                            totalAmount = specialization.quantity,
                        )
                    )
                },
            resourceNeeds = listOf(),
        )
    }

    private suspend fun uploadImageToStorage(image: File, destructionId: String): String {
        val storageRef = Firebase.storage.reference
        val imagesStorageRef = storageRef.child("destructions/${destructionId}")
        val imageStorageRef = imagesStorageRef.child("0.jpg")
        imageStorageRef.putFile(image)
        return imageStorageRef.getDownloadUrl()
    }

    private suspend fun saveDestructionInFirestore(
        destructionId: String,
        destructionDataModel: DestructionDataModel,
        destructionDetailsDataModel: DestructionDetailsDataModel,
    ) {
        val database = Firebase.firestore
        val destructionsCollection = database.collection("destructions")
        val destructionDetailsCollection = database.collection("destruction_details")
        val destructionDocument = destructionsCollection.document(destructionId)
        val destructionDetailsDocument = destructionDetailsCollection.document(destructionId)
        database.batch().apply {
            set(destructionDocument, destructionDataModel)
            set(destructionDetailsDocument, destructionDetailsDataModel)
        }.commit()
    }
}