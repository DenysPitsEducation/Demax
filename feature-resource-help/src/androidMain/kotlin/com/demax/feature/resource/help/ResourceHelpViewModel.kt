package com.demax.feature.resource.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resource.help.mvi.ResourceHelpIntent
import com.demax.feature.resource.help.mvi.ResourceHelpSideEffect
import com.demax.feature.resource.help.mvi.ResourceHelpState
import com.demax.core.navigation.ResourceHelpPayload
import com.demax.feature.resource.help.domain.model.ResourceHelpBottomSheetDomainModel
import com.demax.feature.resource.help.domain.model.ResourceNeedBottomSheetDomainModel
import com.demax.feature.resource.help.domain.repository.ResourceHelpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ResourceHelpViewModel(
    private val payload: ResourceHelpPayload,
    private val repository: ResourceHelpRepository,
) : ViewModel(),
    Mvi<ResourceHelpState, ResourceHelpIntent, ResourceHelpSideEffect> by
    createMviDelegate(
        ResourceHelpState(
            resourceHelpBottomSheet = ResourceHelpBottomSheetDomainModel(
                selectedDate = null,
                needs = payload.resources.map { resource ->
                    ResourceNeedBottomSheetDomainModel(
                        id = resource.id,
                        title = resource.name,
                        quantityText = "1",
                        isSelected = false
                    )
                },
                isButtonEnabled = false
            )
        )
    ) {

    override fun onIntent(intent: ResourceHelpIntent) {
        when (intent) {
            is ResourceHelpIntent.DateChanged -> onResourceHelpDateChanged(intent)
            is ResourceHelpIntent.DateInputClicked -> onResourceHelpDateInputClicked()
            is ResourceHelpIntent.ProvideHelpButtonClicked -> onResourceHelpProvideHelpButtonClicked()
            is ResourceHelpIntent.ResourceOptionClicked -> onResourceHelpResourceOptionClicked(intent)
            is ResourceHelpIntent.ResourceQuantityChanged -> onResourceHelpResourceQuantityChanged(intent)
        }
    }

    private fun onResourceHelpDateChanged(intent: ResourceHelpIntent.DateChanged) {
        updateUiState {
            val localDate = intent.dateMillis?.let {
                Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
            }?.date
            copy(resourceHelpBottomSheet = resourceHelpBottomSheet.copy(selectedDate = localDate))
        }
    }

    private fun onResourceHelpDateInputClicked() {
        viewModelScope.emitSideEffect(ResourceHelpSideEffect.OpenResourceHelpDatePicker)
    }

    private fun onResourceHelpProvideHelpButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        repository.sendResourceResponse(getState().resourceHelpBottomSheet).onSuccess {
            viewModelScope.emitSideEffect(ResourceHelpSideEffect.ShowMessage("Запит на допомогу успішно відправлено"))
        }.onFailure {
            it.printStackTrace()
            viewModelScope.emitSideEffect(ResourceHelpSideEffect.ShowMessage("Помилка відправлення запиту про допомогу"))
        }
        viewModelScope.emitSideEffect(ResourceHelpSideEffect.DismissBottomSheet)
    }

    private fun onResourceHelpResourceOptionClicked(intent: ResourceHelpIntent.ResourceOptionClicked) {
        val resourceHelpBottomSheet = getState().resourceHelpBottomSheet
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

    private fun onResourceHelpResourceQuantityChanged(intent: ResourceHelpIntent.ResourceQuantityChanged) {
        val resourceHelpBottomSheet = getState().resourceHelpBottomSheet
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