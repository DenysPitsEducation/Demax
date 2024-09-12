package com.demax.feature.destruction.edit.mvi

sealed interface DestructionEditSideEffect {
    data class ShowSnackbar(val text: String) : DestructionEditSideEffect
    data object ShowResourcesEditBottomSheet : DestructionEditSideEffect
}