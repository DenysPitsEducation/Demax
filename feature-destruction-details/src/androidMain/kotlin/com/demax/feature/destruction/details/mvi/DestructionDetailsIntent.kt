package com.demax.feature.destruction.details.mvi

sealed interface DestructionDetailsIntent {
    data object ProvideVolunteerHelpClicked : DestructionDetailsIntent
    data object ProvideResourceHelpClicked : DestructionDetailsIntent
    data object AddResourceClicked : DestructionDetailsIntent
}