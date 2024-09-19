package com.demax.feature.profile.mvi

import dev.gitlive.firebase.storage.File

sealed interface ProfileIntent {
    data class ImageSelected(val file: File) : ProfileIntent
    data class NameInputChanged(val input: String) : ProfileIntent
    data class EmailInputChanged(val input: String) : ProfileIntent
    data class PhoneInputChanged(val input: String) : ProfileIntent
    data class AddressInputChanged(val input: String) : ProfileIntent
    data class DescriptionInputChanged(val input: String) : ProfileIntent
    data object SaveButtonClicked : ProfileIntent
    data object HelpHistoryButtonClicked : ProfileIntent
}