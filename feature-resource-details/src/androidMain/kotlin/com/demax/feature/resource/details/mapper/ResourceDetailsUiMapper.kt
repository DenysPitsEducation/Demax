package com.demax.feature.resource.details.mapper

import com.demax.feature.resource.details.domain.model.AmountDomainModel
import com.demax.feature.resource.details.domain.model.DestructionDomainModel
import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel
import com.demax.feature.resource.details.model.AmountUiModel
import com.demax.feature.resource.details.model.DestructionUiModel
import com.demax.feature.resource.details.model.ResourceDetailsUiModel
import com.demax.feature.resource.details.mvi.ResourceDetailsState
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class ResourceDetailsUiMapper {

    fun mapToUiModel(state: ResourceDetailsState): ResourceDetailsUiModel? = state.run {
        return state.resourceDetails?.toUiModel(state.isAdministrator)
    }

    private fun ResourceDetailsDomainModel.toUiModel(isAdministrator: Boolean): ResourceDetailsUiModel {
        return ResourceDetailsUiModel(
            imageUrl = imageUrl,
            status = when (status) {
                ResourceDetailsDomainModel.StatusDomainModel.ACTIVE -> "Активне"
                ResourceDetailsDomainModel.StatusDomainModel.COMPLETED -> "Закрито"
            },
            name = name,
            category = category,
            amount = amount.toUiModel(),
            description = description,
            destruction = destruction.toUiModel(),
            showEditButton = isAdministrator,
        )
    }

    private fun AmountDomainModel.toUiModel() : AmountUiModel {
        return AmountUiModel(
            totalAmount = totalAmount,
            neededAmount = totalAmount - currentAmount,
        )
    }

    private fun DestructionDomainModel.toUiModel(): DestructionUiModel {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        return DestructionUiModel(
            imageUrl = imageUrl,
            destructionDate = formatter.format(destructionDate.toJavaLocalDate()),
            address = address
        )
    }
}