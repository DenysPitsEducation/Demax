package com.demax.feature.profile.mvi

sealed interface ProfileSideEffect {
    data object Open: ProfileSideEffect
    data object OpenMainScreen : ProfileSideEffect
    data object OpenRegistrationScreen : ProfileSideEffect
    data class ShowSnackbar(val text: String) : ProfileSideEffect
}