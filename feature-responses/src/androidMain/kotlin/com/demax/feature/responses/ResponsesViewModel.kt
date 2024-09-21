package com.demax.feature.responses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.responses.domain.ResponsesRepository
import com.demax.feature.responses.domain.model.FilterOptionDomainModel
import com.demax.feature.responses.domain.model.ResponseDomainModel
import com.demax.feature.responses.domain.model.ResponseTypeDomainModel
import com.demax.feature.responses.domain.model.toEnum
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
            filters = listOf(),
            responses = listOf()
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getResponses().onSuccess { responses ->
                updateUiState { copy(
                    filters = getFilters(responses),
                    responses = responses
                ) }
            }.onFailure { it.printStackTrace() }
        }
    }

    private fun getFilters(responses: List<ResponseDomainModel>): List<FilterOptionDomainModel> {
        val types = responses
            .map { it.type.toEnum() }
            .distinct()

        val typeFilters = types.map { type ->
            FilterOptionDomainModel(
                type = FilterOptionDomainModel.Type.ResponseType(type),
                isSelected = false,
            )
        }

        return typeFilters
    }

    override fun onIntent(intent: ResponsesIntent) {
        when (intent) {
            is ResponsesIntent.FilterClicked -> onFilterClicked()
            is ResponsesIntent.FilterOptionClicked -> onFilterOptionClicked(intent)
            is ResponsesIntent.ProfileClicked -> onProfileClicked(intent)
            is ResponsesIntent.DestructionClicked -> onDestructionClicked(intent)
            is ResponsesIntent.ResourceClicked -> onResourceClicked(intent)
            is ResponsesIntent.ApproveButtonClicked -> onApproveButtonClicked(intent)
            is ResponsesIntent.RejectButtonClicked -> onRejectButtonClicked(intent)
        }
    }

    private fun onFilterClicked() {
        viewModelScope.emitSideEffect(ResponsesSideEffect.OpenFilterBottomSheet)
    }

    private fun onFilterOptionClicked(intent: ResponsesIntent.FilterOptionClicked) {
        updateUiState {
            copy(filters = filters.map { filter ->
                if (filter.type == intent.type) {
                    filter.copy(isSelected = !filter.isSelected)
                } else {
                    filter
                }
            })
        }
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