package com.demax.feature.destruction.details.domain.model

import kotlinx.datetime.LocalDate

data class VolunteerHelpBottomSheetDomainModel(
    val selectedDate: LocalDate?,
    val needs: List<VolunteerNeedBottomSheetDomainModel>,
    val isButtonEnabled: Boolean,
)
