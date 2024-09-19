package com.demax.feature.profile.mvi

sealed interface ProfileSideEffect {
    data object OpenHelpHistory : ProfileSideEffect
    data class ShowSnackbar(val text: String) : ProfileSideEffect
}