package com.demax.feature.destruction.edit.mvi

import com.demax.core.domain.model.BuildingTypeDomainModel
import dev.gitlive.firebase.storage.File

sealed interface DestructionEditIntent {
    data class ImageSelected(val file: File) : DestructionEditIntent
    data class AddressChanged(val value: String) : DestructionEditIntent
    data class BuildingTypeValueClicked(val buildingType: BuildingTypeDomainModel) : DestructionEditIntent
    data class PredictionSwitchClicked(val isChecked: Boolean) : DestructionEditIntent
    data class NumberOfApartmentsChanged(val value: String) : DestructionEditIntent
    data class ApartmentsSquareChanged(val value: String) : DestructionEditIntent
    data class DestroyedFloorsChanged(val value: String) : DestructionEditIntent
    data class DestroyedSectionsChanged(val value: String) : DestructionEditIntent
    data class DestroyedPercentageChanged(val value: String) : DestructionEditIntent
    data object ArchitectureMonumentCheckboxClicked : DestructionEditIntent
    data object DangerousSubstancesCheckboxClicked : DestructionEditIntent
    data class DestructionDateSelected(val dateMillis: Long?) : DestructionEditIntent
    data class DestructionTimeSelected(val hour: Int, val minute: Int) : DestructionEditIntent
    data class DescriptionChanged(val value: String) : DestructionEditIntent
    data object EditResourcesClicked : DestructionEditIntent
    data object CreateButtonClicked : DestructionEditIntent

    data class HelpPackagesQuantityChanged(val quantity: Int) : DestructionEditIntent
    data class SpecializationQuantityChanged(val quantity: Int, val specializationName: String) : DestructionEditIntent
    data object SaveResourcesButtonClicked : DestructionEditIntent
}