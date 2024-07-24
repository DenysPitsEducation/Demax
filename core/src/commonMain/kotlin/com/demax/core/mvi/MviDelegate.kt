package com.demax.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MviDelegate<UiState, UiAction, SideEffect> internal constructor(
    initialUiState: UiState,
) : Mvi<UiState, UiAction, SideEffect> {

    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffects: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    override fun onIntent(intent: UiAction) {}

    override fun updateUiState(state: UiState) {
        _uiState.update { state }
    }

    override fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    override fun CoroutineScope.emitSideEffect(effect: SideEffect) {
        launch { _sideEffect.send(effect) }
    }

    override fun getState(): UiState = uiState.value
}

fun <UiState, UiIntent, SideEffect> createMviDelegate(
    initialUiState: UiState,
): Mvi<UiState, UiIntent, SideEffect> = MviDelegate(initialUiState)