package com.demax.feature.responses.domain

import com.demax.feature.responses.domain.model.ResponseDomainModel

interface ResponsesRepository {
    suspend fun getResources(): Result<List<ResponseDomainModel>>
}