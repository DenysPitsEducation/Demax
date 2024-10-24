package com.demax.feature.destruction.edit.data

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import com.demax.core.data.model.AmountDataModel
import com.demax.core.data.model.DestructionDataModel
import com.demax.core.data.model.DestructionStatisticsDataModel
import com.demax.core.data.model.VolunteerNeedDataModel
import com.demax.core.utils.UuidGenerator
import com.demax.feature.destruction.edit.domain.DestructionEditRepository
import com.demax.feature.destruction.edit.domain.model.DestructionEditDomainModel
import com.demax.feature.destruction.edit.domain.model.NeedsDomainModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.storage.File
import dev.gitlive.firebase.storage.storage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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
                destroyedPercentage = null,
                isArchitecturalMonument = false,
                containsDangerousSubstances = false,
                destructionDate = null,
                destructionTime = null,
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
            saveDestructionInFirestore(
                destruction.id,
                destructionDataModel,
            )
        }
    }

    private fun DestructionEditDomainModel.toDestructionDataModel(uploadedImageUrl: String?): DestructionDataModel {
        val dateTime = LocalDateTime(destructionDate ?: throw ValidationException, destructionTime ?: throw ValidationException)
        val formatter = LocalDateTime.Format { byUnicodePattern("yyyy-MM-dd HH:mm") }
        return DestructionDataModel(
            imageUrl = uploadedImageUrl ?: imageUrl,
            buildingType = buildingTypeMapper.mapToDataModel(
                buildingType ?: throw ValidationException
            ),
            address = address ?: throw ValidationException,
            destructionStatistics = DestructionStatisticsDataModel(
                destroyedFloors = destroyedFloors?.toString() ?: throw ValidationException,
                destroyedSections = destroyedSections?.toString() ?: throw ValidationException,
                destroyedPercentage = destroyedPercentage ?: throw ValidationException,
                isArchitecturalMonument = isArchitecturalMonument,
                containsDangerousSubstances = containsDangerousSubstances,
            ),
            destructionDate = formatter.format(dateTime),
            description = description ?: throw ValidationException,
            volunteerNeeds = needsDomainModel.specializations
                .filter { it.quantity > 0 }
                .map { specialization ->
                    VolunteerNeedDataModel(
                        name = specialization.name,
                        amount = AmountDataModel(
                            currentAmount = 0,
                            totalAmount = specialization.quantity,
                        )
                    )
                },
            resourceNeeds = listOf(),
            priority = 10,
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
    ) {
        val database = Firebase.firestore
        val destructionsCollection = database.collection("destructions")
        val destructionDocument = destructionsCollection.document(destructionId)
        destructionDocument.set(destructionDataModel)
    }
}