package com.demax.feature.resources.mapper

import androidx.compose.ui.graphics.Color
import com.demax.feature.resources.domain.model.ResourceDomainModel
import com.demax.feature.resources.model.ResourceUiModel
import com.demax.feature.resources.model.ResourcesUiModel
import com.demax.feature.resources.mvi.ResourcesState

internal class ResourcesUiMapper {

    fun mapToUiModel(state: ResourcesState): ResourcesUiModel = state.run {
        return ResourcesUiModel(
            searchInput = searchInput,
            showAddDestructionButton = isAdministrator,
            resourceUiModels = destructions.map { it.toUiModel() }
        )
    }

    private fun ResourceDomainModel.toUiModel(): ResourceUiModel {
        return ResourceUiModel(
            id = id,
            imageUrl = imageUrl,
            name = name,
            category = category,
            progress = getProgressUiModel(amount),
            status = status.toUiModel()
        )
    }

    private fun getProgressUiModel(amount: ResourceDomainModel.AmountDomainModel): ResourceUiModel.ProgressUiModel {
        val progress = amount.currentAmount * 1.0 / amount.totalAmount
        return ResourceUiModel.ProgressUiModel(
            progress = progress,
            amount = "${amount.currentAmount}/${amount.totalAmount}",
            text = if (progress == 1.0) "Завершено" else "У процесі",
            color = if (progress == 1.0) Color(0xFF198038) else Color(0xFF0043CE),
        )
    }

    private fun ResourceDomainModel.StatusDomainModel.toUiModel(): ResourceUiModel.StatusUiModel {
        return when (this) {
            ResourceDomainModel.StatusDomainModel.ACTIVE -> ResourceUiModel.StatusUiModel(
                text = "Активне",
                background = Color(0xFFF2F487)
            )

            ResourceDomainModel.StatusDomainModel.COMPLETED -> ResourceUiModel.StatusUiModel(
                text = "Виконане",
                background = Color(0xFFC6EB88)
            )
        }
    }
}