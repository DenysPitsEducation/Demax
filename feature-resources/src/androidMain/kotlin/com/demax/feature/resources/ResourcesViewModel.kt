package com.demax.feature.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.feature.resources.mvi.ResourcesIntent
import com.demax.feature.resources.mvi.ResourcesSideEffect
import com.demax.feature.resources.mvi.ResourcesState
import kotlinx.coroutines.launch

class ResourcesViewModel(
    private val repository: ResourcesRepository,
) : ViewModel(),
    Mvi<ResourcesState, ResourcesIntent, ResourcesSideEffect> by
    createMviDelegate(
        ResourcesState(
            searchInput = "",
            isAdministrator = false,
            destructions = listOf()
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getResources().onSuccess { destructions ->
                updateUiState { copy(destructions = destructions) }
            }
        }
    }

    override fun onIntent(intent: ResourcesIntent) {
        when (intent) {
            is ResourcesIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is ResourcesIntent.SortClicked -> onSortClicked()
            is ResourcesIntent.FilterClicked -> onFilterClicked()
            is ResourcesIntent.AddResourceClicked -> onAddResourceClicked()
            is ResourcesIntent.ResourceClicked -> onResourceClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: ResourcesIntent.SearchInputChanged) {
        updateUiState { copy(searchInput = intent.input) }
    }

    private fun onSortClicked() {
        TODO("Not yet implemented")
    }

    private fun onFilterClicked() {
        TODO("Not yet implemented")
    }

    private fun onAddResourceClicked() {
        viewModelScope.emitSideEffect(ResourcesSideEffect.OpenResourceEdit)
    }

    private fun onResourceClicked(intent: ResourcesIntent.ResourceClicked) {
        viewModelScope.emitSideEffect(ResourcesSideEffect.OpenResourceDetails(intent.id))
    }
}