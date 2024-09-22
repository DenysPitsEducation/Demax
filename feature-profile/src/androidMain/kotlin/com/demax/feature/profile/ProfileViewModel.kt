package com.demax.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.profile.domain.ProfileRepository
import com.demax.feature.profile.mvi.ProfileIntent
import com.demax.feature.profile.mvi.ProfileSideEffect
import com.demax.feature.profile.mvi.ProfileState
import com.demax.feature.profile.navigation.ProfilePayload
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val payload: ProfilePayload,
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
            repository.getProfile(payload.profileId ?: Firebase.auth.currentUser!!.uid)
                .onSuccess { destructionDetails ->
                    updateUiState { copy(profile = destructionDetails) }
                }.onFailure { it.printStackTrace() }
        }
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.ImageSelected -> onImageSelected(intent)
            is ProfileIntent.NameInputChanged -> onNameInputChanged(intent)
            is ProfileIntent.EmailInputChanged -> onEmailInputChanged(intent)
            is ProfileIntent.PhoneInputChanged -> onPhoneInputChanged(intent)
            is ProfileIntent.AddressInputChanged -> onAddressInputChanged(intent)
            is ProfileIntent.DescriptionInputChanged -> onDescriptionInputChanged(intent)
            is ProfileIntent.SaveButtonClicked -> onSaveButtonClicked()
            is ProfileIntent.HelpHistoryButtonClicked -> onHelpHistoryButtonClicked()
            is ProfileIntent.LogoutButtonClicked -> onLogoutButtonClicked()
        }
    }

    private fun onImageSelected(intent: ProfileIntent.ImageSelected) {
        updateUiState { copy(profile = profile?.copy(imageFile = intent.file)) }
    }

    private fun onNameInputChanged(intent: ProfileIntent.NameInputChanged) {
        updateUiState { copy(profile = profile?.copy(name = intent.input)) }
    }

    private fun onEmailInputChanged(intent: ProfileIntent.EmailInputChanged) {
        updateUiState { copy(profile = profile?.copy(email = intent.input)) }
    }

    private fun onPhoneInputChanged(intent: ProfileIntent.PhoneInputChanged) {
        updateUiState { copy(profile = profile?.copy(phoneNumber = intent.input)) }
    }

    private fun onAddressInputChanged(intent: ProfileIntent.AddressInputChanged) {
        updateUiState { copy(profile = profile?.copy(address = intent.input)) }
    }

    private fun onDescriptionInputChanged(intent: ProfileIntent.DescriptionInputChanged) {
        updateUiState { copy(profile = profile?.copy(description = intent.input)) }
    }

    private fun onSaveButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = getState().profile
            if (profile != null) {
                repository.saveProfile(profile).onSuccess {
                    viewModelScope.emitSideEffect(ProfileSideEffect.ShowSnackbar("Дані успішно збережено"))
                }.onFailure {
                    it.printStackTrace()
                    viewModelScope.emitSideEffect(ProfileSideEffect.ShowSnackbar("Помилка збереження даних"))
                }
            }
        }
    }

    private fun onHelpHistoryButtonClicked() {
        viewModelScope.emitSideEffect(ProfileSideEffect.OpenHelpHistory(payload.profileId ?: Firebase.auth.currentUser!!.uid))
    }

    private fun onLogoutButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.logout().onSuccess {
                viewModelScope.emitSideEffect(ProfileSideEffect.OpenAuthorizationFlow)
            }.onFailure {
                it.printStackTrace()
                viewModelScope.emitSideEffect(ProfileSideEffect.ShowSnackbar("Помилка виходу з акаунту"))
            }
        }
    }
}