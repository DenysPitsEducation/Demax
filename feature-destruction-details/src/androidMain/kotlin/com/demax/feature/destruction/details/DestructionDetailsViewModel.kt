package com.demax.feature.destruction.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.destruction.details.domain.DestructionDetailsRepository
import com.demax.feature.destruction.details.domain.model.ResourceHelpBottomSheetDomainModel
import com.demax.feature.destruction.details.domain.model.ResourceNeedBottomSheetDomainModel
import com.demax.feature.destruction.details.domain.model.VolunteerNeedBottomSheetDomainModel
import com.demax.feature.destruction.details.domain.model.VolunteerHelpBottomSheetDomainModel
import com.demax.feature.destruction.details.mvi.DestructionDetailsIntent
import com.demax.feature.destruction.details.mvi.DestructionDetailsSideEffect
import com.demax.feature.destruction.details.mvi.DestructionDetailsState
import com.demax.feature.destruction.details.mvi.ResourceHelpBottomSheetIntent
import com.demax.feature.destruction.details.mvi.VolunteerHelpBottomSheetIntent
import com.demax.feature.destruction.details.navigation.DestructionDetailsPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DestructionDetailsViewModel(
    private val payload: DestructionDetailsPayload,
    private val repository: DestructionDetailsRepository,
) : ViewModel(),
    Mvi<DestructionDetailsState, DestructionDetailsIntent, DestructionDetailsSideEffect> by
    createMviDelegate(
        DestructionDetailsState(
            destructionDetails = null,
            volunteerHelpBottomSheet = null,
            resourceHelpBottomSheet = null,
            // TODO Pits: false
            isAdministrator = true,
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getDestructionDetails(payload.id)
                .onSuccess { destructionDetails ->
                    updateUiState {
                        copy(
                            destructionDetails = destructionDetails,
                            volunteerHelpBottomSheet = VolunteerHelpBottomSheetDomainModel(
                                selectedDate = null,
                                needs = destructionDetails.volunteerNeeds
                                    .filter { it.amount.totalAmount != it.amount.currentAmount }
                                    .map { need ->
                                        VolunteerNeedBottomSheetDomainModel(
                                            title = need.name,
                                            isSelected = false,
                                        )
                                    },
                                isButtonEnabled = false,
                            ),
                            resourceHelpBottomSheet = ResourceHelpBottomSheetDomainModel(
                                selectedDate = null,
                                needs = destructionDetails.resourceNeeds
                                    .filter { it.amount.totalAmount != it.amount.currentAmount }
                                    .map { need ->
                                        ResourceNeedBottomSheetDomainModel(
                                            id = need.id,
                                            title = need.name,
                                            quantityText = "1",
                                            isSelected = false,
                                        )
                                    },
                                isButtonEnabled = false,
                            ),
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    override fun onIntent(intent: DestructionDetailsIntent) {
        when (intent) {
            is DestructionDetailsIntent.ProvideVolunteerHelpClicked -> onProvideVolunteerHelpClicked()
            is DestructionDetailsIntent.ProvideResourceHelpClicked -> onProvideResourceHelpClicked()
            is DestructionDetailsIntent.AddResourceClicked -> onAddResourceClicked()

            is VolunteerHelpBottomSheetIntent.DateChanged -> onVolunteerHelpDateChanged(intent)
            is VolunteerHelpBottomSheetIntent.DateInputClicked -> onVolunteerHelpDateInputClicked()
            is VolunteerHelpBottomSheetIntent.ProvideHelpButtonClicked -> onVolunteerHelpProvideHelpButtonClicked()
            is VolunteerHelpBottomSheetIntent.SpecializationOptionClicked -> onVolunteerHelpSpecializationOptionClicked(
                intent
            )

            is ResourceHelpBottomSheetIntent.DateChanged -> onResourceHelpDateChanged(intent)
            is ResourceHelpBottomSheetIntent.DateInputClicked -> onResourceHelpDateInputClicked()
            is ResourceHelpBottomSheetIntent.ProvideHelpButtonClicked -> onResourceHelpProvideHelpButtonClicked()
            is ResourceHelpBottomSheetIntent.ResourceOptionClicked -> onResourceHelpResourceOptionClicked(intent)
            is ResourceHelpBottomSheetIntent.ResourceQuantityChanged -> onResourceHelpResourceQuantityChanged(intent)
        }
    }

    private fun onProvideVolunteerHelpClicked() {
        viewModelScope.emitSideEffect(DestructionDetailsSideEffect.OpenVolunteerHelpBottomSheet)
    }

    private fun onProvideResourceHelpClicked() {
        viewModelScope.emitSideEffect(DestructionDetailsSideEffect.OpenResourceHelpBottomSheet)
    }

    private fun onAddResourceClicked() {
        viewModelScope.emitSideEffect(DestructionDetailsSideEffect.OpenResourceEditScreen)
    }

    private fun onVolunteerHelpDateChanged(intent: VolunteerHelpBottomSheetIntent.DateChanged) {
        updateUiState {
            val localDate = intent.dateMillis?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            }?.date
            copy(volunteerHelpBottomSheet = volunteerHelpBottomSheet?.copy(selectedDate = localDate))
        }
    }

    private fun onVolunteerHelpDateInputClicked() {
        viewModelScope.emitSideEffect(DestructionDetailsSideEffect.OpenVolunteerHelpDatePicker)
    }

    private fun onVolunteerHelpProvideHelpButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        repository.sendVolunteerResponse(payload.id, getState().volunteerHelpBottomSheet!!).onSuccess {
            viewModelScope.emitSideEffect(DestructionDetailsSideEffect.ShowSnackbar("Запит на допомогу успішно відправлено"))
        }.onFailure {
            it.printStackTrace()
            viewModelScope.emitSideEffect(DestructionDetailsSideEffect.ShowSnackbar("Помилка відправлення запиту про допомогу"))
        }
    }

    private fun onVolunteerHelpSpecializationOptionClicked(intent: VolunteerHelpBottomSheetIntent.SpecializationOptionClicked) {
        val volunteerNeedsBottomSheet = getState().volunteerHelpBottomSheet ?: return
        val updatedNeeds = volunteerNeedsBottomSheet.needs.map { need ->
            if (need.title == intent.specialization) {
                need.copy(isSelected = !need.isSelected)
            } else {
                need
            }
        }
        updateUiState {
            copy(volunteerHelpBottomSheet = volunteerNeedsBottomSheet.copy(needs = updatedNeeds))
        }
    }

    private fun onResourceHelpDateChanged(intent: ResourceHelpBottomSheetIntent.DateChanged) {
        updateUiState {
            val localDate = intent.dateMillis?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            }?.date
            copy(resourceHelpBottomSheet = resourceHelpBottomSheet?.copy(selectedDate = localDate))
        }
    }

    private fun onResourceHelpDateInputClicked() {
        viewModelScope.emitSideEffect(DestructionDetailsSideEffect.OpenResourceHelpDatePicker)
    }

    private fun onResourceHelpProvideHelpButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        repository.sendResourceResponse(payload.id, getState().resourceHelpBottomSheet!!).onSuccess {
            viewModelScope.emitSideEffect(DestructionDetailsSideEffect.ShowSnackbar("Запит на допомогу успішно відправлено"))
        }.onFailure {
            it.printStackTrace()
            viewModelScope.emitSideEffect(DestructionDetailsSideEffect.ShowSnackbar("Помилка відправлення запиту про допомогу"))
        }
    }

    private fun onResourceHelpResourceOptionClicked(intent: ResourceHelpBottomSheetIntent.ResourceOptionClicked) {
        val resourceHelpBottomSheet = getState().resourceHelpBottomSheet ?: return
        val updatedNeeds = resourceHelpBottomSheet.needs.map { need ->
            if (need.title == intent.resource) {
                need.copy(isSelected = !need.isSelected)
            } else {
                need
            }
        }
        updateUiState {
            copy(resourceHelpBottomSheet = resourceHelpBottomSheet.copy(needs = updatedNeeds))
        }
    }

    private fun onResourceHelpResourceQuantityChanged(intent: ResourceHelpBottomSheetIntent.ResourceQuantityChanged) {
        val resourceHelpBottomSheet = getState().resourceHelpBottomSheet ?: return
        val updatedNeeds = resourceHelpBottomSheet.needs.map { need ->
            if (need.id == intent.id) {
                need.copy(quantityText = intent.quantity)
            } else {
                need
            }
        }
        updateUiState {
            copy(resourceHelpBottomSheet = resourceHelpBottomSheet.copy(needs = updatedNeeds))
        }
    }
}