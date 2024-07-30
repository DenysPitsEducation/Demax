package com.demax.feature.resource.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.mvi.ResourceDetailsIntent
import com.demax.feature.resource.details.mvi.ResourceDetailsSideEffect
import com.demax.feature.resource.details.mvi.ResourceDetailsState
import kotlinx.coroutines.launch

class ResourceDetailsViewModel(
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
            repository.getDestructionDetails().onSuccess { destructionDetails ->
                updateUiState { copy(resourceDetails = destructionDetails) }
            }
        }
    }

    override fun onIntent(intent: ResourceDetailsIntent) {
        when (intent) {
            is ResourceDetailsIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is ResourceDetailsIntent.SortClicked -> onSortClicked()
            is ResourceDetailsIntent.FilterClicked -> onFilterClicked()
            is ResourceDetailsIntent.AddResourceClicked -> onAddResourceClicked()
            is ResourceDetailsIntent.ResourceClicked -> onResourceClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: ResourceDetailsIntent.SearchInputChanged) {
        //updateUiState { copy(searchInput = intent.input) }
    }

    private fun onSortClicked() {
        TODO("Not yet implemented")
    }

    private fun onFilterClicked() {
        TODO("Not yet implemented")
    }

    private fun onAddResourceClicked() {
        TODO("Not yet implemented")
    }

    private fun onResourceClicked(intent: ResourceDetailsIntent.ResourceClicked) {
        TODO("Not yet implemented")
    }
}