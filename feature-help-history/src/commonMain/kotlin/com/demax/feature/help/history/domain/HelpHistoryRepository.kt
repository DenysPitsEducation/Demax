package com.demax.feature.help.history.domain

import com.demax.feature.help.history.domain.model.HelpDomainModel

interface HelpHistoryRepository {
    suspend fun getHelpHistory(profileId: String): Result<List<HelpDomainModel>>
}