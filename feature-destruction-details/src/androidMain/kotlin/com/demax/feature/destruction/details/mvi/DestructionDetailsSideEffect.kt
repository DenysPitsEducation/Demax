package com.demax.feature.destruction.details.mvi

sealed interface DestructionDetailsSideEffect {
    data object OpenVolunteerHelpBottomSheet : DestructionDetailsSideEffect
    data object OpenVolunteerHelpDatePicker : DestructionDetailsSideEffect
    data class OpenResourceDetails(val id: String) : DestructionDetailsSideEffect
    data object OpenResourceHelpBottomSheet : DestructionDetailsSideEffect
    data object OpenResourceEditScreen : DestructionDetailsSideEffect
    data class ShowSnackbar(val text: String) : DestructionDetailsSideEffect
}