package com.demax.feature.resource.help.domain.repository

import com.demax.feature.resource.help.domain.model.ResourceHelpBottomSheetDomainModel

interface ResourceHelpRepository {
    suspend fun sendResourceResponse(
        resourceHelpBottomSheet: ResourceHelpBottomSheetDomainModel,
    ): Result<Unit>
}