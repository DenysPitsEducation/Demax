package com.demax.feature.destruction.edit.domain.model

import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.domain.model.ResourceCategoryDomainModel
import dev.gitlive.firebase.storage.File
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class DestructionEditDomainModel(
    val id: String,
    val imageUrl: String?,
    val imageFile: File?,
    val address: String?,
    val buildingType: BuildingTypeDomainModel?,
    val predictionSwitch: PredictionSwitchDomainModel?,
    val numberOfApartments: Int?,
    val apartmentsSquare: Int?,
    val destroyedFloors: Int?,
    val destroyedSections: Int?,
    val destroyedPercentage: Int?,
    val isArchitecturalMonument: Boolean,
    val containsDangerousSubstances: Boolean,
    val destructionDate: LocalDate?,
    val destructionTime: LocalTime?,
    val description: String?,
    val needsDomainModel: NeedsDomainModel,
)
