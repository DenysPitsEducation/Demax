package com.demax.feature.destruction.details.mvi

import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.VolunteerHelpBottomSheetDomainModel

data class DestructionDetailsState(
    val destructionDetails: DestructionDetailsDomainModel?,
    val volunteerHelpBottomSheet: VolunteerHelpBottomSheetDomainModel?,
    val isAdministrator: Boolean,
)
