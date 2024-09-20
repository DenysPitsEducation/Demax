package com.demax.feature.profile.mvi

sealed interface ProfileSideEffect {
    data class OpenHelpHistory(val profileId: String) : ProfileSideEffect
    data class ShowSnackbar(val text: String) : ProfileSideEffect
}