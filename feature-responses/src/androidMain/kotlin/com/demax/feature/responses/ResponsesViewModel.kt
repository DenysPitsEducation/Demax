package com.demax.feature.responses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.responses.domain.ResponsesRepository
import com.demax.feature.responses.mvi.ResponsesIntent
import com.demax.feature.responses.mvi.ResponsesSideEffect
import com.demax.feature.responses.mvi.ResponsesState
import kotlinx.coroutines.launch

class ResponsesViewModel(
    private val repository: ResponsesRepository,
) : ViewModel(),
    Mvi<ResponsesState, ResponsesIntent, ResponsesSideEffect> by
    createMviDelegate(
        ResponsesState(
            responses = listOf()
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getResources().onSuccess { destructions ->
                updateUiState { copy(responses = destructions) }
            }
        }
    }

    override fun onIntent(intent: ResponsesIntent) {
        when (intent) {
            is ResponsesIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is ResponsesIntent.SortClicked -> onSortClicked()
            is ResponsesIntent.FilterClicked -> onFilterClicked()
            is ResponsesIntent.AddDestructionClicked -> onAddDestructionClicked()
            is ResponsesIntent.DestructionClicked -> onDestructionClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: ResponsesIntent.SearchInputChanged) {
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

    private fun onDestructionClicked(intent: ResponsesIntent.DestructionClicked) {
        TODO("Not yet implemented")
    }
}