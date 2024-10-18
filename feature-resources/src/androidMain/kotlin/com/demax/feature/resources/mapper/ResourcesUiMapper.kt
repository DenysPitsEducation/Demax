package com.demax.feature.resources.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.domain.model.StatusDomainModel
import com.demax.core.ui.mapper.ResourceCategoryUiMapper
import com.demax.feature.resources.domain.model.FilterOptionDomainModel
import com.demax.feature.resources.domain.model.ResourceDomainModel
import com.demax.feature.resources.model.FilterOptionUiModel
import com.demax.feature.resources.model.FilterUiModel
import com.demax.feature.resources.model.ResourceUiModel
import com.demax.feature.resources.model.ResourcesUiModel
import com.demax.feature.resources.mvi.ResourcesState

internal class ResourcesUiMapper(
    private val categoryMapper: ResourceCategoryUiMapper,
) {

    fun mapToUiModel(state: ResourcesState): ResourcesUiModel = state.run {
        return ResourcesUiModel(
            searchInput = searchInput,
            filterUiModels = filters.toUiModel(),
            showAddResourceButton = isAdministrator,
            resourceUiModels = visibleResources.map { it.toUiModel() }
        )
    }

    private fun List<FilterOptionDomainModel>.toUiModel(): List<FilterUiModel> {
        val groups = groupBy { it.type::class }
        return groups.mapNotNull { (typeClass, options) ->
            FilterUiModel(
                title = when (typeClass) {
                    FilterOptionDomainModel.Type.Category::class -> "Категорія"
                    FilterOptionDomainModel.Type.Status::class -> "Статус"
                    else -> return@mapNotNull null
                },
                options = options.map { it.toUiModel() }
            )
        }
    }

    private fun FilterOptionDomainModel.toUiModel(): FilterOptionUiModel {
        return FilterOptionUiModel(
            type = type,
            title = when (type) {
                is FilterOptionDomainModel.Type.Category -> categoryMapper.mapToUiModel(
                    type.category
                )

                is FilterOptionDomainModel.Type.Status -> when (type.status) {
                    StatusDomainModel.ACTIVE -> "Активне"
                    StatusDomainModel.COMPLETED -> "Виконане"
                }
            },
            isSelected = isSelected
        )
    }

    private fun ResourceDomainModel.toUiModel(): ResourceUiModel {
        return ResourceUiModel(
            id = id,
            imageUrl = imageUrl,
            name = name,
            category = categoryMapper.mapToUiModel(category),
            progress = getProgressUiModel(amount),
            status = status.toUiModel()
        )
    }

    private fun getProgressUiModel(amount: ResourceDomainModel.AmountDomainModel): ResourceUiModel.ProgressUiModel {
        val progress = if (amount.totalAmount != 0) {
            amount.currentAmount * 1.0 / amount.totalAmount
        } else 0.0
        return ResourceUiModel.ProgressUiModel(
            progress = progress,
            amount = "${amount.currentAmount}/${amount.totalAmount}",
            text = if (progress == 1.0) "Завершено" else "У процесі",
            color = if (progress == 1.0) Color(0xFF198038) else Color(0xFF0043CE),
        )
    }

    private fun StatusDomainModel.toUiModel(): ResourceUiModel.StatusUiModel {
        return when(this) {
            StatusDomainModel.ACTIVE -> ResourceUiModel.StatusUiModel(
                text = "Активне",
                background = Color(0xFFF2F487)
            )
            StatusDomainModel.COMPLETED -> ResourceUiModel.StatusUiModel(
                text = "Виконане",
                background = Color(0xFFC6EB88)
            )
        }
    }
}