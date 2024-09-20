package com.demax.feature.help.history.mvi

import com.demax.feature.help.history.domain.model.HelpDomainModel

data class HelpHistoryState(
    val responses: List<HelpDomainModel>,
)
