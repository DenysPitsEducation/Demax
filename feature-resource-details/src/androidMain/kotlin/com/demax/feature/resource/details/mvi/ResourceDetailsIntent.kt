package com.demax.feature.resource.details.mvi

sealed interface ResourceDetailsIntent {
    data object ProvideHelpClicked : ResourceDetailsIntent
    data object ModifyResourceClicked : ResourceDetailsIntent
}