package com.demax.feature.help.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.help.history.domain.HelpHistoryRepository
import com.demax.feature.help.history.domain.model.HelpDomainModel
import com.demax.feature.help.history.mvi.HelpHistoryIntent
import com.demax.feature.help.history.mvi.HelpHistorySideEffect
import com.demax.feature.help.history.mvi.HelpHistoryState
import com.demax.feature.help.history.navigation.HelpHistoryPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelpHistoryViewModel(
    private val payload: HelpHistoryPayload,
    private val repository: HelpHistoryRepository,
) : ViewModel(),
    Mvi<HelpHistoryState, HelpHistoryIntent, HelpHistorySideEffect> by
    createMviDelegate(
        HelpHistoryState(
            responses = listOf()
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getHelpHistory(payload.profileId).onSuccess { destructions ->
                updateUiState { copy(responses = destructions) }
            }.onFailure { it.printStackTrace() }
        }
    }

    override fun onIntent(intent: HelpHistoryIntent) {
        when (intent) {
            is HelpHistoryIntent.DestructionClicked -> onDestructionClicked(intent)
            is HelpHistoryIntent.ResourceClicked -> onResourceClicked(intent)
        }
    }

    private fun onDestructionClicked(intent: HelpHistoryIntent.DestructionClicked) {
        viewModelScope.emitSideEffect(HelpHistorySideEffect.OpenDestructionDetails(intent.destructionId))
    }

    private fun onResourceClicked(intent: HelpHistoryIntent.ResourceClicked) {
        viewModelScope.emitSideEffect(HelpHistorySideEffect.OpenResourceDetails(intent.resourceId))
    }
}