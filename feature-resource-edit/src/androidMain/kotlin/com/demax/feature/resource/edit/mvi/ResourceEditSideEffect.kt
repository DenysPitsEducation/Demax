package com.demax.feature.resource.edit.mvi

sealed interface ResourceEditSideEffect {
    data object OpenPhotoPicker : ResourceEditSideEffect
    data class ShowSnackbar(val text: String) : ResourceEditSideEffect
}