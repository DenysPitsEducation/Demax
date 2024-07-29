package com.demax.feature.destruction.details.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.utils.formatFractionalPart
import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionStatisticsDomainModel
import com.demax.feature.destruction.details.domain.model.NeedDomainModel
import com.demax.feature.destruction.details.model.DestructionDetailsUiModel
import com.demax.feature.destruction.details.model.DestructionStatisticsUiModel
import com.demax.feature.destruction.details.model.NeedUiModel
import com.demax.feature.destruction.details.model.ProgressUiModel
import com.demax.feature.destruction.details.model.ResourceNeedsBlockUiModel
import com.demax.feature.destruction.details.model.VolunteerNeedsBlockUiModel
import com.demax.feature.destruction.details.mvi.DestructionDetailsState
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class DestructionDetailsUiMapper {

    fun mapToUiModel(state: DestructionDetailsState): DestructionDetailsUiModel? = state.run {
        return state.destructionDetails?.toUiModel(state.isAdministrator)
    }

    private fun DestructionDetailsDomainModel.toUiModel(isAdministrator: Boolean): DestructionDetailsUiModel {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        return DestructionDetailsUiModel(
            imageUrl = imageUrl,
            status = when (status) {
                DestructionDetailsDomainModel.StatusDomainModel.ACTIVE -> "Активне"
                DestructionDetailsDomainModel.StatusDomainModel.COMPLETED -> "Закрито"
            },
            buildingType = buildingType,
            address = address,
            destructionStatistics = destructionStatistics.toUiModel(),
            destructionDate = formatter.format(destructionDate.toJavaLocalDate()),
            description = description,
            volunteerNeedsBlock = getVolunteerNeedsBlockUiModel(volunteerNeeds),
            resourceNeedsBlock = getResourceNeedsBlockUiModel(resourceNeeds, isAdministrator),
        )
    }

    private fun DestructionStatisticsDomainModel.toUiModel() : DestructionStatisticsUiModel {
        return DestructionStatisticsUiModel(
            destroyedFloors = destroyedFloors,
            destroyedSections = destroyedSections,
        )
    }

    private fun getVolunteerNeedsBlockUiModel(needs: List<NeedDomainModel>): VolunteerNeedsBlockUiModel {
        return VolunteerNeedsBlockUiModel(
            needs = needs.map { need ->
                NeedUiModel(
                    name = need.name,
                    progress = getProgressUiModel(need.amount),
                )
            }
        )
    }

    private fun getResourceNeedsBlockUiModel(needs: List<NeedDomainModel>, isAdministrator: Boolean): ResourceNeedsBlockUiModel {
        return ResourceNeedsBlockUiModel(
            needs = needs.map { need ->
                NeedUiModel(
                    name = need.name,
                    progress = getProgressUiModel(need.amount),
                )
            },
            showAddResourcesButton = isAdministrator,
        )
    }

    private fun getProgressUiModel(amount: AmountDomainModel): ProgressUiModel {
        val progress = amount.currentAmount * 1.0 / amount.totalAmount
        return ProgressUiModel(
            progress = progress,
            amount = "${amount.currentAmount}/${amount.totalAmount}",
            text = if (progress == 1.0) "Завершено" else "У процесі",
            color = if (progress == 1.0) Color(0xFF198038) else Color(0xFF0043CE),
        )
    }
}