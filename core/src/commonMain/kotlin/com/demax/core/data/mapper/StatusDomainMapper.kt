package com.demax.core.data.mapper

import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.domain.model.StatusDomainModel

class StatusDomainMapper {
    fun mapToDomainModel(status: String): StatusDomainModel {
        return when (status) {
            "completed" -> StatusDomainModel.COMPLETED
            else -> StatusDomainModel.ACTIVE
        }
    }
}