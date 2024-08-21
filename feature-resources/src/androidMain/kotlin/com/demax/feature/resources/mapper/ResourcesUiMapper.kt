package com.demax.feature.resources.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.domain.model.ResourceCategoryDomainModel
import com.demax.core.ui.mapper.ResourceCategoryUiMapper
import com.demax.feature.resources.domain.model.ResourceDomainModel
import com.demax.feature.resources.model.ResourceUiModel
import com.demax.feature.resources.model.ResourcesUiModel
import com.demax.feature.resources.mvi.ResourcesState

internal class ResourcesUiMapper(
    private val categoryMapper: ResourceCategoryUiMapper,
) {

    fun mapToUiModel(state: ResourcesState): ResourcesUiModel = state.run {
        return ResourcesUiModel(
            searchInput = searchInput,
            showAddDestructionButton = isAdministrator,
            resourceUiModels = resources.map { it.toUiModel() }
        )
    }

    private fun ResourceDomainModel.toUiModel(): ResourceUiModel {
        return ResourceUiModel(
            id = id,
            imageUrl = imageUrl,
            name = name,
            category = categoryMapper.mapToUiModel(category),
            progress = getProgressUiModel(amount),
            status = amount.toUiModel()
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

    private fun ResourceDomainModel.AmountDomainModel.toUiModel(): ResourceUiModel.StatusUiModel {
        return when {
            currentAmount < totalAmount -> ResourceUiModel.StatusUiModel(
                text = "Активне",
                background = Color(0xFFF2F487)
            )

            else -> ResourceUiModel.StatusUiModel(
                text = "Виконане",
                background = Color(0xFFC6EB88)
            )
        }
    }
}