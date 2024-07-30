package com.demax.feature.profile.mapper

import com.demax.feature.profile.domain.model.ProfileDomainModel
import com.demax.feature.profile.model.ProfileUiModel
import com.demax.feature.profile.mvi.ProfileState
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class ProfileUiMapper {

    fun mapToUiModel(state: ProfileState): ProfileUiModel? = state.run {
        return state.profile?.toUiModel()
    }

    private fun ProfileDomainModel.toUiModel(): ProfileUiModel {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        return ProfileUiModel(
            imageUrl = imageUrl,
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            address = address,
            about = about,
            specializations = specializations,
            registrationDate = formatter.format(registrationDate.toJavaLocalDate()),
            helpsCount = helpsCount
        )
    }
}