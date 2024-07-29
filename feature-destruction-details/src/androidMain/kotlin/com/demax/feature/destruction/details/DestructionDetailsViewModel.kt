package com.demax.feature.destruction.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.destruction.details.domain.DestructionDetailsRepository
import com.demax.feature.destruction.details.mvi.DestructionDetailsIntent
import com.demax.feature.destruction.details.mvi.DestructionDetailsSideEffect
import com.demax.feature.destruction.details.mvi.DestructionDetailsState
import kotlinx.coroutines.launch

class DestructionDetailsViewModel(
    private val repository: DestructionDetailsRepository,
) : ViewModel(),
    Mvi<DestructionDetailsState, DestructionDetailsIntent, DestructionDetailsSideEffect> by
    createMviDelegate(
        DestructionDetailsState(
            destructionDetails = null,
            // TODO Pits: false
            isAdministrator = true,
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getDestructionDetails().onSuccess { destructionDetails ->
                updateUiState { copy(destructionDetails = destructionDetails) }
            }
        }
    }

    override fun onIntent(intent: DestructionDetailsIntent) {
        when (intent) {
            is DestructionDetailsIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is DestructionDetailsIntent.SortClicked -> onSortClicked()
            is DestructionDetailsIntent.FilterClicked -> onFilterClicked()
            is DestructionDetailsIntent.AddDestructionClicked -> onAddDestructionClicked()
            is DestructionDetailsIntent.DestructionClicked -> onDestructionClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: DestructionDetailsIntent.SearchInputChanged) {
        //updateUiState { copy(searchInput = intent.input) }
    }

    private fun onSortClicked() {
        TODO("Not yet implemented")
    }

    private fun onFilterClicked() {
        TODO("Not yet implemented")
    }

    private fun onAddDestructionClicked() {
        TODO("Not yet implemented")
    }

    private fun onDestructionClicked(intent: DestructionDetailsIntent.DestructionClicked) {
        TODO("Not yet implemented")
    }
}