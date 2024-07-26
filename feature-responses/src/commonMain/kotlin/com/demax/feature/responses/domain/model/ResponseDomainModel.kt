package com.demax.feature.responses.domain.model

data class ResponseDomainModel(
    val id: Long,
    val profile: ProfileDomainModel,
    val type: ResponseTypeDomainModel,
    val status: StatusDomainModel,
) {

    enum class StatusDomainModel {
        IDLE, APPROVED, REJECTED
    }
}
