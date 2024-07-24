package com.demax.feature.destructions

import androidx.lifecycle.ViewModel
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.destructions.domain.DestructionsRepository

class DestructionsViewModel(
    private val repository: DestructionsRepository,
) : ViewModel(),
    Mvi<DestructionsState, DestructionsIntent, DestructionsSideEffect> by
    createMviDelegate(DestructionsState(email = "", password = "")) {


    override fun onIntent(intent: DestructionsIntent) {
        /*when (intent) {
            is DestructionsIntent.EmailInputChanged -> onEmailInputChanged(intent)
            is DestructionsIntent.PasswordInputChanged -> onPasswordInputChanged(intent)
            is DestructionsIntent.OpenPasswordResetScreen -> onOpenPasswordResetScreen()
            is DestructionsIntent.LoginClicked -> onLoginClicked()
            is DestructionsIntent.CreateAccountClicked -> onCreateAccountClicked()
        }*/
    }
}