package com.demax.feature.responses.mvi

import com.demax.feature.responses.domain.model.ResponseDomainModel

data class ResponsesState(
    val responses: List<ResponseDomainModel>,
)
