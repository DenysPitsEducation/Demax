package com.demax.feature.authorization.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.authorization.common.domain.AuthorizationRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthorizationRepository,
) : ViewModel(),
    Mvi<LoginState, LoginIntent, LoginSideEffect> by
    createMviDelegate(LoginState(email = "", password = "")) {


    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailInputChanged -> onEmailInputChanged(intent)
            is LoginIntent.PasswordInputChanged -> onPasswordInputChanged(intent)
            is LoginIntent.OpenPasswordResetScreen -> onOpenPasswordResetScreen()
            is LoginIntent.LoginClicked -> onLoginClicked()
            is LoginIntent.CreateAccountClicked -> onCreateAccountClicked()
        }
    }

    private fun onEmailInputChanged(intent: LoginIntent.EmailInputChanged) {
        updateUiState { copy(email = intent.emailInput) }
    }

    private fun onPasswordInputChanged(intent: LoginIntent.PasswordInputChanged) {
        updateUiState { copy(password = intent.passwordInput) }
    }

    private fun onOpenPasswordResetScreen() {
        viewModelScope.emitSideEffect(LoginSideEffect.OpenPasswordResetScreen)
    }

    private fun onLoginClicked() = viewModelScope.launch {
        repository.login(
            email = getState().email,
            password = getState().password
        ).onSuccess {
            emitSideEffect(LoginSideEffect.OpenMainScreen)
        }.onFailure {
            emitSideEffect(LoginSideEffect.ShowSnackbar("Помилка авторизації. Перевірте правильність введення даних"))
        }
    }

    private fun onCreateAccountClicked() {
        viewModelScope.emitSideEffect(LoginSideEffect.OpenRegistrationScreen)
    }
}