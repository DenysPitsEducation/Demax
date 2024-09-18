package com.demax.feature.responses.domain

import com.demax.feature.responses.domain.model.ResponseDomainModel

interface ResponsesRepository {
    suspend fun getResponses(): Result<List<ResponseDomainModel>>
    suspend fun approveResponse(response: ResponseDomainModel): Result<Unit>
    suspend fun rejectResponse(response: ResponseDomainModel): Result<Unit>
}