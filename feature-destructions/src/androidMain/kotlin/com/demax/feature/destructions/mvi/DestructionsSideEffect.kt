package com.demax.feature.destructions.mvi

sealed interface DestructionsSideEffect {
    data object OpenDestructionEditScreen : DestructionsSideEffect
    data class OpenDestructionDetails(val id: String) : DestructionsSideEffect
    data object OpenSortBottomSheet : DestructionsSideEffect
    data object OpenFilterBottomSheet : DestructionsSideEffect
}