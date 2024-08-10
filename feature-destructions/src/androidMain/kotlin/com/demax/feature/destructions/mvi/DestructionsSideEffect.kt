package com.demax.feature.destructions.mvi

sealed interface DestructionsSideEffect {
    data class OpenDestructionDetails(val id: String) : DestructionsSideEffect
    data object OpenSortBottomSheet : DestructionsSideEffect
    data object OpenFilterBottomSheet : DestructionsSideEffect
}