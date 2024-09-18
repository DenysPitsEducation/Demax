package com.demax.feature.destruction.details.mvi

sealed interface ResourceHelpBottomSheetIntent : DestructionDetailsIntent {
    data class ResourceOptionClicked(val resource: String) : DestructionDetailsIntent
    data class ResourceQuantityChanged(val id: String, val quantity: String) : DestructionDetailsIntent
    data class DateChanged(val dateMillis: Long?) : DestructionDetailsIntent
    data object ProvideHelpButtonClicked : DestructionDetailsIntent
    data object DateInputClicked : DestructionDetailsIntent
}