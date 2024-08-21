package com.demax.feature.resource.details.mapper

import com.demax.core.ui.mapper.ResourceCategoryUiMapper
import com.demax.feature.resource.details.domain.model.AmountDomainModel
import com.demax.feature.resource.details.domain.model.DestructionDomainModel
import com.demax.feature.resource.details.domain.model.ResourceDetailsDomainModel
import com.demax.feature.resource.details.model.AmountUiModel
import com.demax.feature.resource.details.model.DestructionUiModel
import com.demax.feature.resource.details.model.ResourceDetailsUiModel
import com.demax.feature.resource.details.mvi.ResourceDetailsState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class ResourceDetailsUiMapper(
    private val categoryMapper: ResourceCategoryUiMapper,
) {

    fun mapToUiModel(state: ResourceDetailsState): ResourceDetailsUiModel? = state.run {
        return state.resourceDetails?.toUiModel(state.isAdministrator)
    }

    private fun ResourceDetailsDomainModel.toUiModel(isAdministrator: Boolean): ResourceDetailsUiModel {
        return ResourceDetailsUiModel(
            imageUrl = imageUrl,
            status = if (amount.currentAmount < amount.totalAmount) {
                "Активне"
            } else {
                "Закрито"
            },
            name = name,
            category = categoryMapper.mapToUiModel(category),
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
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return DestructionUiModel(
            imageUrl = imageUrl,
            destructionDate = formatter.format(destructionDate),
            address = address
        )
    }
}