package com.demax.feature.profile.mapper

import com.demax.feature.profile.domain.model.ProfileDomainModel
import com.demax.feature.profile.model.ProfileUiModel
import com.demax.feature.profile.mvi.ProfileState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

internal class ProfileUiMapper {

    fun mapToUiModel(state: ProfileState): ProfileUiModel? = state.run {
        return state.profile?.toUiModel()
    }

    private fun ProfileDomainModel.toUiModel(): ProfileUiModel {
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return ProfileUiModel(
            isGuest = isGuest,
            image = imageFile?.uri ?: imageUrl,
            name = name,
            email = email,
            phoneNumber = phoneNumber.orEmpty(),
            address = address.orEmpty(),
            description = description.orEmpty(),
            specializations = specializations.orEmpty(),
            registrationDate = formatter.format(registrationDate),
            helpsCount = helpsCount
        )
    }
}