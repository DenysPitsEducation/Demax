package com.demax.feature.resource.help.domain.model

import kotlinx.datetime.LocalDate

data class ResourceHelpBottomSheetDomainModel(
    val selectedDate: LocalDate?,
    val needs: List<ResourceNeedBottomSheetDomainModel>,
    val isButtonEnabled: Boolean,
)
