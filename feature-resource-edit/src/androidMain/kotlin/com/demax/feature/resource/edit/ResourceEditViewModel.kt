package com.demax.feature.resource.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resource.edit.domain.ResourceEditRepository
import com.demax.feature.resource.edit.mvi.ResourceEditIntent
import com.demax.feature.resource.edit.mvi.ResourceEditSideEffect
import com.demax.feature.resource.edit.mvi.ResourceEditState
import kotlinx.coroutines.launch

class ResourceEditViewModel(
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
            repository.getDestructionDetails().onSuccess { destructionDetails ->
                updateUiState { copy(domainModel = destructionDetails) }
            }
        }
    }

    override fun onIntent(intent: ResourceEditIntent) {
        when (intent) {
            is ResourceEditIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is ResourceEditIntent.SortClicked -> onSortClicked()
            is ResourceEditIntent.FilterClicked -> onFilterClicked()
            is ResourceEditIntent.AddResourceClicked -> onAddResourceClicked()
            is ResourceEditIntent.ResourceClicked -> onResourceClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: ResourceEditIntent.SearchInputChanged) {
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

    private fun onResourceClicked(intent: ResourceEditIntent.ResourceClicked) {
        TODO("Not yet implemented")
    }
}