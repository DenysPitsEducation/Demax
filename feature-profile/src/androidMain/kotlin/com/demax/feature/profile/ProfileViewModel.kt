package com.demax.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.profile.domain.ProfileRepository
import com.demax.feature.profile.mvi.ProfileIntent
import com.demax.feature.profile.mvi.ProfileSideEffect
import com.demax.feature.profile.mvi.ProfileState
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository,
) : ViewModel(),
    Mvi<ProfileState, ProfileIntent, ProfileSideEffect> by
    createMviDelegate(
        ProfileState(
            profile = null,
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getDestructionDetails().onSuccess { destructionDetails ->
                updateUiState { copy(profile = destructionDetails) }
            }
        }
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is ProfileIntent.SortClicked -> onSortClicked()
            is ProfileIntent.FilterClicked -> onFilterClicked()
            is ProfileIntent.AddDestructionClicked -> onAddDestructionClicked()
            is ProfileIntent.DestructionClicked -> onDestructionClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: ProfileIntent.SearchInputChanged) {
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

    private fun onDestructionClicked(intent: ProfileIntent.DestructionClicked) {
        TODO("Not yet implemented")
    }
}