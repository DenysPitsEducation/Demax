package com.demax.feature.destruction.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.destruction.edit.domain.DestructionEditRepository
import com.demax.feature.destruction.edit.domain.model.PredictionSwitchDomainModel
import com.demax.feature.destruction.edit.mvi.DestructionEditIntent
import com.demax.feature.destruction.edit.mvi.DestructionEditSideEffect
import com.demax.feature.destruction.edit.mvi.DestructionEditState
import com.demax.feature.destruction.edit.navigation.DestructionEditPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DestructionEditViewModel(
    private val payload: DestructionEditPayload,
    private val repository: DestructionEditRepository,
) : ViewModel(),
    Mvi<DestructionEditState, DestructionEditIntent, DestructionEditSideEffect> by
    createMviDelegate(
        DestructionEditState(
            domainModel = null,
        )
    ) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDestructionEditModel(payload.destructionId)
                .onSuccess { destructionEditModel ->
                    updateUiState {
                        copy(
                            domainModel = destructionEditModel,
                        )
                    }
                }.onFailure { it.printStackTrace() }
        }
    }

    override fun onIntent(intent: DestructionEditIntent) {
        when (intent) {
            is DestructionEditIntent.ImageSelected -> onImageSelected(intent)
            is DestructionEditIntent.AddressChanged -> onAddressChanged(intent)
            is DestructionEditIntent.BuildingTypeValueClicked -> onBuildingTypeValueClicked(intent)
            is DestructionEditIntent.PredictionSwitchClicked -> onPredictionSwitchClicked(intent)
            is DestructionEditIntent.NumberOfApartmentsChanged -> onNumberOfApartmentsChanged(intent)
            is DestructionEditIntent.ApartmentsSquareChanged -> onApartmentsSquareChanged(intent)
            is DestructionEditIntent.DestroyedFloorsChanged -> onDestroyedFloorsChanged(intent)
            is DestructionEditIntent.DestroyedSectionsChanged -> onDestroyedSectionsChanged(intent)
            is DestructionEditIntent.DestroyedPercentageChanged -> onDestroyedPercentageChanged(
                intent
            )

            is DestructionEditIntent.ArchitectureMonumentCheckboxClicked -> onArchitectureMonumentCheckboxClicked()
            is DestructionEditIntent.DangerousSubstancesCheckboxClicked -> onDangerousSubstancesCheckboxClicked()
            is DestructionEditIntent.DestructionDateSelected -> onDestructionDateSelected(intent)
            is DestructionEditIntent.DestructionTimeSelected -> onDestructionTimeSelected(intent)
            is DestructionEditIntent.DescriptionChanged -> onDescriptionChanged(intent)
            is DestructionEditIntent.EditResourcesClicked -> onEditResourcesClicked()
            is DestructionEditIntent.CreateButtonClicked -> onCreateButtonClicked()

            is DestructionEditIntent.HelpPackagesQuantityChanged -> onHelpPackagesQuantityChanged(
                intent
            )

            is DestructionEditIntent.SpecializationQuantityChanged -> onSpecializationQuantityChanged(
                intent
            )

            is DestructionEditIntent.SaveResourcesButtonClicked -> onSaveResourcesButtonClicked()
        }
    }

    private fun onImageSelected(intent: DestructionEditIntent.ImageSelected) {
        updateUiState { copy(domainModel = domainModel?.copy(imageFile = intent.file)) }
    }

    private fun onAddressChanged(intent: DestructionEditIntent.AddressChanged) {
        updateUiState { copy(domainModel = domainModel?.copy(address = intent.value)) }
    }

    private fun onBuildingTypeValueClicked(intent: DestructionEditIntent.BuildingTypeValueClicked) {
        updateUiState {
            val buildingType = intent.buildingType
            val predictionSwitch = if (buildingType == BuildingTypeDomainModel.RESIDENTIAL) {
                PredictionSwitchDomainModel(isChecked = false)
            } else null
            copy(
                domainModel = domainModel?.copy(
                    buildingType = intent.buildingType,
                    predictionSwitch = predictionSwitch
                )
            )
        }
    }

    private fun onPredictionSwitchClicked(intent: DestructionEditIntent.PredictionSwitchClicked) {
        updateUiState {
            val updatedPredictionSwitch =
                domainModel?.predictionSwitch?.copy(isChecked = intent.isChecked)
            copy(domainModel = domainModel?.copy(predictionSwitch = updatedPredictionSwitch))
        }
    }

    private fun onNumberOfApartmentsChanged(intent: DestructionEditIntent.NumberOfApartmentsChanged) {
        val numberOfApartments = intent.value.toIntOrNull()
        if (numberOfApartments != null) {
            updateUiState { copy(domainModel = domainModel?.copy(numberOfApartments = numberOfApartments)) }
        }
    }

    private fun onApartmentsSquareChanged(intent: DestructionEditIntent.ApartmentsSquareChanged) {
        val apartmentsSquare = intent.value.toIntOrNull()
        if (apartmentsSquare != null) {
            updateUiState { copy(domainModel = domainModel?.copy(apartmentsSquare = apartmentsSquare)) }
        }
    }

    private fun onDestroyedFloorsChanged(intent: DestructionEditIntent.DestroyedFloorsChanged) {
        val destroyedFloors = intent.value.toIntOrNull()
        if (destroyedFloors != null) {
            updateUiState { copy(domainModel = domainModel?.copy(destroyedFloors = destroyedFloors)) }
        }
    }

    private fun onDestroyedSectionsChanged(intent: DestructionEditIntent.DestroyedSectionsChanged) {
        val destroyedSections = intent.value.toIntOrNull()
        if (destroyedSections != null) {
            updateUiState { copy(domainModel = domainModel?.copy(destroyedSections = destroyedSections)) }
        }
    }

    private fun onDestroyedPercentageChanged(intent: DestructionEditIntent.DestroyedPercentageChanged) {
        val destroyedPercentage = intent.value.toIntOrNull()?.coerceIn(0, 100)
        if (destroyedPercentage != null) {
            updateUiState { copy(domainModel = domainModel?.copy(destroyedPercentage = destroyedPercentage)) }
        }
    }

    private fun onArchitectureMonumentCheckboxClicked() {
        updateUiState {
            copy(
                domainModel = domainModel?.copy(
                    isArchitecturalMonument = !domainModel.isArchitecturalMonument
                )
            )
        }
    }

    private fun onDangerousSubstancesCheckboxClicked() {
        updateUiState {
            copy(
                domainModel = domainModel?.copy(
                    containsDangerousSubstances = !domainModel.containsDangerousSubstances
                )
            )
        }
    }

    private fun onDestructionDateSelected(intent: DestructionEditIntent.DestructionDateSelected) {
        updateUiState {
            val localDate = intent.dateMillis?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            }?.date
            copy(domainModel = domainModel?.copy(destructionDate = localDate))
        }
    }

    private fun onDestructionTimeSelected(intent: DestructionEditIntent.DestructionTimeSelected) {
        updateUiState {
            copy(
                domainModel = domainModel?.copy(
                    destructionTime = LocalTime(
                        hour = intent.hour,
                        minute = intent.minute
                    )
                )
            )
        }
    }

    private fun onDescriptionChanged(intent: DestructionEditIntent.DescriptionChanged) {
        updateUiState { copy(domainModel = domainModel?.copy(description = intent.value)) }
    }

    private fun onEditResourcesClicked() {
        viewModelScope.emitSideEffect(DestructionEditSideEffect.ShowResourcesEditBottomSheet)
    }

    private fun onCreateButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        val destructionEditModel = getState().domainModel
        if (destructionEditModel != null) {
            repository.saveDestruction(destructionEditModel).onSuccess {
                emitSideEffect(DestructionEditSideEffect.ShowSnackbar("Дані успішно збережено"))
            }.onFailure {
                it.printStackTrace()
                emitSideEffect(DestructionEditSideEffect.ShowSnackbar("Помилка збереження даних"))
            }
        }
    }

    private fun onHelpPackagesQuantityChanged(intent: DestructionEditIntent.HelpPackagesQuantityChanged) {
        updateUiState {
            copy(
                domainModel = domainModel?.copy(
                    needsDomainModel = domainModel.needsDomainModel.copy(
                        helpPackages = intent.quantity,
                    )
                )
            )
        }
    }

    private fun onSpecializationQuantityChanged(intent: DestructionEditIntent.SpecializationQuantityChanged) {
        val needs = getState().domainModel?.needsDomainModel
        val updatedSpecializations = needs?.specializations?.map { specialization ->
            if (specialization.name == intent.specializationName) {
                specialization.copy(quantity = intent.quantity)
            } else {
                specialization
            }
        }

        if (updatedSpecializations != null) {
            updateUiState {
                copy(
                    domainModel = domainModel?.copy(
                        needsDomainModel = domainModel.needsDomainModel.copy(
                            specializations = updatedSpecializations,
                        )
                    )
                )
            }
        }
    }

    private fun onSaveResourcesButtonClicked() {
        // do nothing for now
    }
}