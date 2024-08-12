package com.demax.feature.destruction.details.mvi

sealed interface VolunteerHelpBottomSheetIntent : DestructionDetailsIntent {
    data class SpecializationOptionClicked(val specialization: String) : DestructionDetailsIntent
    data class DateChanged(val dateMillis: Long?) : DestructionDetailsIntent
    data object ProvideHelpButtonClicked : DestructionDetailsIntent
    data object DateInputClicked : DestructionDetailsIntent
}