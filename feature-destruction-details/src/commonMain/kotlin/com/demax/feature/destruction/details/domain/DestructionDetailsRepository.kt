package com.demax.feature.destruction.details.domain

import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.VolunteerHelpBottomSheetDomainModel

interface DestructionDetailsRepository {
    suspend fun getDestructionDetails(id: String): Result<DestructionDetailsDomainModel>

    suspend fun sendVolunteerResponse(
        destructionId: String,
        volunteerHelpBottomSheet: VolunteerHelpBottomSheetDomainModel
    ): Result<Unit>
}