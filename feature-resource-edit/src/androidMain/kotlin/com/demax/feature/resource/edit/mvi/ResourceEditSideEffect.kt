package com.demax.feature.resource.edit.mvi

sealed interface ResourceEditSideEffect {
    data class ShowSnackbar(val text: String) : ResourceEditSideEffect
}