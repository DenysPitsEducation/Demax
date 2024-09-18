package com.demax.feature.resource.help.mvi

sealed interface ResourceHelpIntent {
    data class ResourceOptionClicked(val resource: String) : ResourceHelpIntent
    data class ResourceQuantityChanged(val id: String, val quantity: String) : ResourceHelpIntent
    data class DateChanged(val dateMillis: Long?) : ResourceHelpIntent
    data object ProvideHelpButtonClicked : ResourceHelpIntent
    data object DateInputClicked : ResourceHelpIntent
}