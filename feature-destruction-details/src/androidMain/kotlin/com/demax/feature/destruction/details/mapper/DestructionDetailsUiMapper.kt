package com.demax.feature.destruction.details.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.domain.model.StatusDomainModel
import com.demax.core.ui.mapper.BuildingTypeUiMapper
import com.demax.feature.destruction.details.domain.model.AmountDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionDetailsDomainModel
import com.demax.feature.destruction.details.domain.model.DestructionStatisticsDomainModel
import com.demax.feature.destruction.details.domain.model.NeedDomainModel
import com.demax.feature.destruction.details.domain.model.ResourceHelpBottomSheetDomainModel
import com.demax.feature.destruction.details.domain.model.VolunteerHelpBottomSheetDomainModel
import com.demax.feature.destruction.details.model.DestructionDetailsUiModel
import com.demax.feature.destruction.details.model.DestructionStatisticsUiModel
import com.demax.feature.destruction.details.model.NeedUiModel
import com.demax.feature.destruction.details.model.ProgressUiModel
import com.demax.feature.destruction.details.model.ResourceHelpBottomSheetUiModel
import com.demax.feature.destruction.details.model.ResourceNeedBottomSheetUiModel
import com.demax.feature.destruction.details.model.ResourceNeedsBlockUiModel
import com.demax.feature.destruction.details.model.VolunteerNeedBottomSheetUiModel
import com.demax.feature.destruction.details.model.VolunteerNeedsBlockUiModel
import com.demax.feature.destruction.details.model.VolunteerHelpBottomSheetUiModel
import com.demax.feature.destruction.details.mvi.DestructionDetailsState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

internal class DestructionDetailsUiMapper(
    private val buildingTypeUiMapper: BuildingTypeUiMapper,
) {

    fun mapToDestructionDetailsUiModel(state: DestructionDetailsState): DestructionDetailsUiModel? = state.run {
        return state.destructionDetails?.toUiModel(state.isAdministrator)
    }

    private fun DestructionDetailsDomainModel.toUiModel(isAdministrator: Boolean): DestructionDetailsUiModel {
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return DestructionDetailsUiModel(
            imageUrl = imageUrl,
            status = when (status) {
                StatusDomainModel.ACTIVE -> "Активне"
                StatusDomainModel.COMPLETED -> "Закрито"
            },
            buildingType = buildingTypeUiMapper.mapToUiModel(buildingType),
            address = address,
            destructionStatistics = destructionStatistics.toUiModel(),
            destructionDate = formatter.format(destructionDate),
            description = description,
            volunteerNeedsBlock = getVolunteerNeedsBlockUiModel(volunteerNeeds),
            resourceNeedsBlock = getResourceNeedsBlockUiModel(resourceNeeds, isAdministrator),
        )
    }

    fun mapToVolunteerBottomSheetUiModel(model: VolunteerHelpBottomSheetDomainModel): VolunteerHelpBottomSheetUiModel = model.run {
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return VolunteerHelpBottomSheetUiModel(
            dateInputText = selectedDate?.let { formatter.format(it) },
            needs = needs.map { need ->
                VolunteerNeedBottomSheetUiModel(
                    title = need.title,
                    isSelected = need.isSelected,
                )
            },
            isButtonEnabled = selectedDate != null && needs.any { it.isSelected },
        )
    }

    fun mapToResourceBottomSheetUiModel(model: ResourceHelpBottomSheetDomainModel): ResourceHelpBottomSheetUiModel = model.run {
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return ResourceHelpBottomSheetUiModel(
            dateInputText = selectedDate?.let { formatter.format(it) },
            needs = needs.map { need ->
                ResourceNeedBottomSheetUiModel(
                    title = need.title,
                    quantity = need.quantity?.toString(),
                    isSelected = need.isSelected,
                )
            },
            isButtonEnabled = selectedDate != null && needs.any { it.isSelected },
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
            },
            showHelpButton = needs.any { it.amount.totalAmount != it.amount.currentAmount },
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
            showHelpButton = needs.any { it.amount.totalAmount != it.amount.currentAmount },
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