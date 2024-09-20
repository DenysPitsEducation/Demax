package com.demax.feature.help.history.domain.model

data class HelpDomainModel(
    val id: String,
    val type: HelpTypeDomainModel,
    val status: StatusDomainModel,
) {

    enum class StatusDomainModel {
        IDLE, APPROVED, REJECTED
    }
}
