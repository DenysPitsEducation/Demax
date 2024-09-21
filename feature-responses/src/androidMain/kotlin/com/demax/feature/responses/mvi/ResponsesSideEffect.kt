package com.demax.feature.responses.mvi

sealed interface ResponsesSideEffect {
    data object OpenFilterBottomSheet : ResponsesSideEffect
    data class OpenProfile(val id: String): ResponsesSideEffect
    data class OpenDestructionDetails(val id: String): ResponsesSideEffect
    data class OpenResourceDetails(val id: String): ResponsesSideEffect
    data class ShowSnackbar(val text: String) : ResponsesSideEffect
}