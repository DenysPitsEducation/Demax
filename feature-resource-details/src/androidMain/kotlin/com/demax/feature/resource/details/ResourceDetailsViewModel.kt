package com.demax.feature.resource.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.mvi.ResourceDetailsIntent
import com.demax.feature.resource.details.mvi.ResourceDetailsSideEffect
import com.demax.feature.resource.details.mvi.ResourceDetailsState
import com.demax.feature.resource.details.navigation.ResourceDetailsPayload
import kotlinx.coroutines.launch

class ResourceDetailsViewModel(
    private val payload: ResourceDetailsPayload,
    private val repository: ResourceDetailsRepository,
) : ViewModel(),
    Mvi<ResourceDetailsState, ResourceDetailsIntent, ResourceDetailsSideEffect> by
    createMviDelegate(
        ResourceDetailsState(
            resourceDetails = null,
            // TODO Pits: false
            isAdministrator = true,
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getResourceDetails(payload.id).onSuccess { destructionDetails ->
                updateUiState { copy(resourceDetails = destructionDetails) }
            }.onFailure { it.printStackTrace() }
        }
    }

    override fun onIntent(intent: ResourceDetailsIntent) {
        when (intent) {
            is ResourceDetailsIntent.ModifyResourceClicked -> onModifyResourceClicked()
            is ResourceDetailsIntent.ProvideHelpClicked -> onProvideHelpClicked()
        }
    }

    private fun onModifyResourceClicked() {
        viewModelScope.emitSideEffect(ResourceDetailsSideEffect.OpenResourceEditScreen)
    }

    private fun onProvideHelpClicked() {
        viewModelScope.emitSideEffect(ResourceDetailsSideEffect.OpenResourceHelpBottomSheet)
    }
}