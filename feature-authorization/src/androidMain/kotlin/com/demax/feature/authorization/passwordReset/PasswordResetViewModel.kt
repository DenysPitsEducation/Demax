package com.demax.feature.authorization.passwordReset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.authorization.common.domain.AuthorizationRepository
import kotlinx.coroutines.launch

class PasswordResetViewModel(
    private val authorizationRepository: AuthorizationRepository,
) : ViewModel(),
    Mvi<PasswordResetState, PasswordResetIntent, PasswordResetSideEffect> by
    createMviDelegate(PasswordResetState(email = "")) {

    override fun onIntent(intent: PasswordResetIntent) {
        when (intent) {
            is PasswordResetIntent.EmailInputChanged -> onEmailInputChanged(intent)
            is PasswordResetIntent.SendClicked -> onSendClicked()
        }
    }

    private fun onEmailInputChanged(intent: PasswordResetIntent.EmailInputChanged) {
        updateUiState { copy(email = intent.emailInput) }
    }

    private fun onSendClicked() = viewModelScope.launch {
        authorizationRepository.resetPassword(getState().email).onSuccess {
            emitSideEffect(PasswordResetSideEffect.ShowSnackbar("Лист надіслано на пошту"))
        }.onFailure {
            emitSideEffect(
                PasswordResetSideEffect.ShowSnackbar(
                    text = it.localizedMessage
                        ?: "Помилка при відправленні листа. Перевірте правильність введення пошти та спробуйте ще раз"
                )
            )
        }
    }
}