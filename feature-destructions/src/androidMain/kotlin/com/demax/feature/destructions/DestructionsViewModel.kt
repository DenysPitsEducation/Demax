package com.demax.feature.destructions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.destructions.domain.DestructionsRepository
import com.demax.feature.destructions.mvi.DestructionsIntent
import com.demax.feature.destructions.mvi.DestructionsSideEffect
import com.demax.feature.destructions.mvi.DestructionsState
import kotlinx.coroutines.launch

class DestructionsViewModel(
    private val repository: DestructionsRepository,
) : ViewModel(),
    Mvi<DestructionsState, DestructionsIntent, DestructionsSideEffect> by
    createMviDelegate(
        DestructionsState(
            searchInput = "",
            isAdministrator = false,
            destructions = listOf()
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getDestructions().onSuccess { destructions ->
                updateUiState { copy(destructions = destructions) }
            }
        }
    }

    override fun onIntent(intent: DestructionsIntent) {
        when (intent) {
            is DestructionsIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is DestructionsIntent.SortClicked -> onSortClicked()
            is DestructionsIntent.FilterClicked -> onFilterClicked()
            is DestructionsIntent.AddDestructionClicked -> onAddDestructionClicked()
            is DestructionsIntent.DestructionClicked -> onDestructionClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: DestructionsIntent.SearchInputChanged) {
        updateUiState { copy(searchInput = intent.input) }
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

    private fun onDestructionClicked(intent: DestructionsIntent.DestructionClicked) {
        viewModelScope.emitSideEffect(DestructionsSideEffect.OpenDestructionDetails(intent.id))
    }
}