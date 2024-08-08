package com.demax.feature.resource.edit.mapper

import com.demax.feature.resource.edit.domain.model.ResourceEditDomainModel
import com.demax.feature.resource.edit.model.ResourceEditUiModel
import com.demax.feature.resource.edit.mvi.ResourceEditState

internal class ResourceEditUiMapper {

    fun mapToUiModel(state: ResourceEditState): ResourceEditUiModel? = state.run {
        return state.domainModel?.toUiModel()
    }

    private fun ResourceEditDomainModel.toUiModel(): ResourceEditUiModel {
        return ResourceEditUiModel(
            imageUrl = imageUrl,
            status = when (status) {
                ResourceEditDomainModel.StatusDomainModel.ACTIVE -> "Активне"
                ResourceEditDomainModel.StatusDomainModel.COMPLETED -> "Закрито"
            },
            name = name,
            category = category,
            amount = amount,
            description = description,
            destruction = destruction.address,
        )
    }
}