package com.demax.feature.destructions.mvi

sealed interface DestructionsSideEffect {
    data class OpenDestructionDetails(val id: Long): DestructionsSideEffect
}