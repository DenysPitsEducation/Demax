package com.demax.feature.authorization.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.authorization.common.domain.AuthorizationRepository
import com.demax.feature.authorization.login.LoginSideEffect
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val repository: AuthorizationRepository,
) : ViewModel(),
    Mvi<RegistrationState, RegistrationIntent, RegistrationSideEffect> by
    createMviDelegate(RegistrationState(name = "", email = "", password = "", passwordConfirmation = "")) {

    override fun onIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.NameInputChanged -> onNameInputChanged(intent)
            is RegistrationIntent.EmailInputChanged -> onEmailInputChanged(intent)
            is RegistrationIntent.PasswordInputChanged -> onPasswordInputChanged(intent)
            is RegistrationIntent.PasswordConfirmationInputChanged -> onPasswordConfirmationInputChanged(
                intent
            )

            is RegistrationIntent.CreateAccountClicked -> onCreateAccountClicked()
            is RegistrationIntent.LoginClicked -> onLoginClicked()
        }
    }

    private fun onNameInputChanged(intent: RegistrationIntent.NameInputChanged) {
        updateUiState { copy(name = intent.input) }
    }

    private fun onEmailInputChanged(intent: RegistrationIntent.EmailInputChanged) {
        updateUiState { copy(email = intent.input) }
    }

    private fun onPasswordInputChanged(intent: RegistrationIntent.PasswordInputChanged) {
        updateUiState { copy(password = intent.input) }
    }

    private fun onPasswordConfirmationInputChanged(intent: RegistrationIntent.PasswordConfirmationInputChanged) {
        updateUiState { copy(passwordConfirmation = intent.input) }
    }

    private fun onCreateAccountClicked() = viewModelScope.launch {
        val name = getState().name
        val email = getState().email
        val password = getState().password
        val passwordConfirmation = getState().passwordConfirmation
        if (password == passwordConfirmation) {
            repository.register(
                name = name,
                email = email,
                password = password
            ).onSuccess {
                emitSideEffect(RegistrationSideEffect.OpenMainScreen)
            }.onFailure {
                emitSideEffect(
                    RegistrationSideEffect.ShowSnackbar(
                        text = it.localizedMessage
                            ?: "Помилка реєстрації. Перевірте правильність введення даних"
                    )
                )
            }
        } else {
            emitSideEffect(RegistrationSideEffect.ShowSnackbar("Паролі не збігаються"))
        }
    }

    private fun onLoginClicked() {
        viewModelScope.emitSideEffect(RegistrationSideEffect.OpenLoginScreen)
    }
}