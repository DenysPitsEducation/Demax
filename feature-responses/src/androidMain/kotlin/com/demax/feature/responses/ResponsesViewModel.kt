package com.demax.feature.responses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.responses.domain.ResponsesRepository
import com.demax.feature.responses.domain.model.ResponseDomainModel
import com.demax.feature.responses.mvi.ResponsesIntent
import com.demax.feature.responses.mvi.ResponsesSideEffect
import com.demax.feature.responses.mvi.ResponsesState
import kotlinx.coroutines.Dispatchers
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
            repository.getResponses().onSuccess { destructions ->
                updateUiState { copy(responses = destructions) }
            }.onFailure { it.printStackTrace() }
        }
    }

    override fun onIntent(intent: ResponsesIntent) {
        when (intent) {
            is ResponsesIntent.SortClicked -> onSortClicked()
            is ResponsesIntent.FilterClicked -> onFilterClicked()
            is ResponsesIntent.ProfileClicked -> onProfileClicked(intent)
            is ResponsesIntent.DestructionClicked -> onDestructionClicked(intent)
            is ResponsesIntent.ResourceClicked -> onResourceClicked(intent)
            is ResponsesIntent.ApproveButtonClicked -> onApproveButtonClicked(intent)
            is ResponsesIntent.RejectButtonClicked -> onRejectButtonClicked(intent)
        }
    }

    private fun onSortClicked() {
        TODO("Not yet implemented")
    }

    private fun onFilterClicked() {
        TODO("Not yet implemented")
    }

    private fun onProfileClicked(intent: ResponsesIntent.ProfileClicked) {
        viewModelScope.emitSideEffect(ResponsesSideEffect.OpenProfile(intent.profileId))
    }

    private fun onDestructionClicked(intent: ResponsesIntent.DestructionClicked) {
        viewModelScope.emitSideEffect(ResponsesSideEffect.OpenDestructionDetails(intent.destructionId))
    }

    private fun onResourceClicked(intent: ResponsesIntent.ResourceClicked) {
        viewModelScope.emitSideEffect(ResponsesSideEffect.OpenResourceDetails(intent.resourceId))
    }

    private fun onApproveButtonClicked(intent: ResponsesIntent.ApproveButtonClicked) = viewModelScope.launch(Dispatchers.IO) {
        val response = getState().responses.first { it.id == intent.responseId }
        val updatedResponse = response.copy(status = ResponseDomainModel.StatusDomainModel.APPROVED)
        updateUiState {
            val updatedResponses = responses.map { response ->
                if (response.id == intent.responseId) {
                    updatedResponse
                } else {
                    response
                }
            }
            copy(responses = updatedResponses)
        }
        repository.approveResponse(updatedResponse).onFailure { it.printStackTrace() }
    }

    private fun onRejectButtonClicked(intent: ResponsesIntent.RejectButtonClicked) = viewModelScope.launch(Dispatchers.IO) {
        val response = getState().responses.first { it.id == intent.responseId }
        val updatedResponse = response.copy(status = ResponseDomainModel.StatusDomainModel.REJECTED)
        updateUiState {
            val updatedResponses = responses.map { response ->
                if (response.id == intent.responseId) {
                    updatedResponse
                } else {
                    response
                }
            }
            copy(responses = updatedResponses)
        }
        repository.rejectResponse(updatedResponse)
    }
}