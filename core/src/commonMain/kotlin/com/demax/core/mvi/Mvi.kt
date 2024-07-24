package com.demax.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Mvi<UiState, UiIntent, SideEffect> {
    val uiState: StateFlow<UiState>
    val sideEffects: Flow<SideEffect>

    fun onIntent(intent: UiIntent)

    fun updateUiState(block: UiState.() -> UiState)

    fun updateUiState(state: UiState)

    fun CoroutineScope.emitSideEffect(effect: SideEffect)

    fun getState(): UiState
}