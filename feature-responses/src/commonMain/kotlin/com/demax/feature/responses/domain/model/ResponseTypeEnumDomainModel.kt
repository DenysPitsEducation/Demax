package com.demax.feature.responses.domain.model

enum class ResponseTypeEnumDomainModel {
    VOLUNTEER, RESOURCE
}

fun ResponseTypeDomainModel.toEnum() = when (this) {
    is ResponseTypeDomainModel.Resource -> ResponseTypeEnumDomainModel.RESOURCE
    is ResponseTypeDomainModel.Volunteer -> ResponseTypeEnumDomainModel.VOLUNTEER
}
