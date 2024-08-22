package com.demax.feature.resource.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resource.edit.domain.ResourceEditRepository
import com.demax.feature.resource.edit.mvi.ResourceEditIntent
import com.demax.feature.resource.edit.mvi.ResourceEditSideEffect
import com.demax.feature.resource.edit.mvi.ResourceEditState
import com.demax.feature.resource.edit.navigation.ResourceEditPayload
import kotlinx.coroutines.launch

class ResourceEditViewModel(
    private val payload: ResourceEditPayload,
    private val repository: ResourceEditRepository,
) : ViewModel(),
    Mvi<ResourceEditState, ResourceEditIntent, ResourceEditSideEffect> by
    createMviDelegate(
        ResourceEditState(
            domainModel = null,
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getResourceEditModel(payload.resourceId)
                .onSuccess { resourceEditModel ->
                    updateUiState { copy(domainModel = resourceEditModel) }
                }.onFailure { it.printStackTrace() }
        }
    }

    override fun onIntent(intent: ResourceEditIntent) {
        when (intent) {
            is ResourceEditIntent.ImageSelected -> onImageSelected(intent)
            is ResourceEditIntent.NameChanged -> onNameChanged(intent)
            is ResourceEditIntent.CategoryValueClicked -> onCategoryValueClicked(intent)
            is ResourceEditIntent.TotalAmountChanged -> onTotalAmountChanged(intent)
            is ResourceEditIntent.CurrentAmountChanged -> onCurrentAmountChanged(intent)
            is ResourceEditIntent.DescriptionChanged -> onDescriptionChanged(intent)
            is ResourceEditIntent.DestructionValueClicked -> onDestructionValueClicked(intent)
            is ResourceEditIntent.SaveButtonClicked -> onSaveButtonClicked()
        }
    }

    private fun onImageSelected(intent: ResourceEditIntent.ImageSelected) {
        updateUiState { copy(domainModel = domainModel?.copy(imageFile = intent.file)) }
    }

    private fun onNameChanged(intent: ResourceEditIntent.NameChanged) {
        updateUiState { copy(domainModel = domainModel?.copy(name = intent.value)) }
    }

    private fun onCategoryValueClicked(intent: ResourceEditIntent.CategoryValueClicked) {
        updateUiState { copy(domainModel = domainModel?.copy(category = intent.category)) }
    }

    private fun onTotalAmountChanged(intent: ResourceEditIntent.TotalAmountChanged) {
        val amount = intent.value.toIntOrNull()
        if (amount != null) {
            updateUiState { copy(domainModel = domainModel?.copy(totalAmount = amount)) }
        }
    }

    private fun onCurrentAmountChanged(intent: ResourceEditIntent.CurrentAmountChanged) {
        val amount = intent.value.toIntOrNull()
        if (amount != null) {
            updateUiState { copy(domainModel = domainModel?.copy(currentAmount = amount)) }
        }
    }

    private fun onDescriptionChanged(intent: ResourceEditIntent.DescriptionChanged) {
        updateUiState { copy(domainModel = domainModel?.copy(description = intent.value)) }
    }

    private fun onDestructionValueClicked(intent: ResourceEditIntent.DestructionValueClicked) {
        updateUiState {
            val selectedDestruction = domainModel?.availableDestructions?.firstOrNull { it.id == intent.destructionId }
            copy(domainModel = domainModel?.copy(selectedDestruction = selectedDestruction))
        }
    }

    private fun onSaveButtonClicked() = viewModelScope.launch {
        val resourceModel = getState().domainModel
        if (resourceModel != null) {
            repository.saveResource(resourceModel).onSuccess {
                emitSideEffect(ResourceEditSideEffect.ShowSnackbar("Дані успішно збережено"))
            }.onFailure {
                it.printStackTrace()
                emitSideEffect(ResourceEditSideEffect.ShowSnackbar("Помилка збереження даних"))
            }
        }
    }
}