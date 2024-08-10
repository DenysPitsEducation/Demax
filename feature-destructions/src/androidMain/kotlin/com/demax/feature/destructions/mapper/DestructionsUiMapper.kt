package com.demax.feature.destructions.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.utils.formatFractionalPart
import com.demax.feature.destructions.domain.model.DestructionDomainModel
import com.demax.feature.destructions.domain.model.FilterOptionDomainModel
import com.demax.feature.destructions.domain.model.SortOptionDomainModel
import com.demax.feature.destructions.domain.model.SortingTypeDomainModel
import com.demax.feature.destructions.model.DestructionItemUiModel
import com.demax.feature.destructions.model.DestructionsUiModel
import com.demax.feature.destructions.model.FilterOptionUiModel
import com.demax.feature.destructions.model.FilterUiModel
import com.demax.feature.destructions.model.SortUiModel
import com.demax.feature.destructions.mvi.DestructionsState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

internal class DestructionsUiMapper(
    private val buildingTypeUiMapper: BuildingTypeUiMapper,
) {

    fun mapToUiModel(state: DestructionsState): DestructionsUiModel = state.run {
        return DestructionsUiModel(
            sortUiModels = sorts.map { it.toUiModel() },
            filterUiModels = filters.toUiModel(),
            showAddDestructionButton = isAdministrator,
            destructionItemUiModels = visibleDestructions.map { it.toUiModel() }
        )
    }

    private fun SortOptionDomainModel.toUiModel(): SortUiModel {
        return SortUiModel(
            type = type,
            title = when (type) {
                SortingTypeDomainModel.BY_PRIORITY -> "За пріоритетом"
                SortingTypeDomainModel.BY_DATE -> "За датою руйнування"
            },
            isSelected = isSelected,
        )
    }

    private fun List<FilterOptionDomainModel>.toUiModel(): List<FilterUiModel> {
        val groups = groupBy { it.type::class }
        return groups.mapNotNull { (typeClass, options) ->
            FilterUiModel(
                title = when (typeClass) {
                    FilterOptionDomainModel.Type.BuildingType::class -> "Тип будівлі"
                    FilterOptionDomainModel.Type.Specialization::class -> "Спеціалізація"
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
                is FilterOptionDomainModel.Type.BuildingType -> buildingTypeUiMapper.mapToUiModel(
                    type.buildingType
                )

                is FilterOptionDomainModel.Type.Specialization -> type.specialization
                is FilterOptionDomainModel.Type.Status -> when (type.status) {
                    DestructionDomainModel.StatusDomainModel.ACTIVE -> "Активне"
                    DestructionDomainModel.StatusDomainModel.COMPLETED -> "Виконане"
                }
            },
            isSelected = isSelected
        )
    }

    private fun DestructionDomainModel.toUiModel(): DestructionItemUiModel {
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return DestructionItemUiModel(
            id = id,
            imageUrl = imageUrl,
            buildingType = buildingTypeUiMapper.mapToUiModel(buildingType),
            address = address,
            destructionDate = formatter.format(destructionDate),
            resourceProgress = getProgressUiModel(resourceProgress),
            volunteerProgress = getProgressUiModel(volunteerProgress),
            specializations = specializations,
            status = status.toUiModel()
        )
    }

    private fun getProgressUiModel(progress: Double): DestructionItemUiModel.ProgressUiModel {
        return DestructionItemUiModel.ProgressUiModel(
            progress = progress,
            percentage = "${(progress * 100).formatFractionalPart()}%",
            text = if (progress == 1.0) "Завершено" else "У процесі",
            color = if (progress == 1.0) Color(0xFF198038) else Color(0xFF0043CE),
        )
    }

    private fun DestructionDomainModel.StatusDomainModel.toUiModel(): DestructionItemUiModel.StatusUiModel {
        return when (this) {
            DestructionDomainModel.StatusDomainModel.ACTIVE -> DestructionItemUiModel.StatusUiModel(
                text = "Активне",
                background = Color(0xFFF2F487)
            )

            DestructionDomainModel.StatusDomainModel.COMPLETED -> DestructionItemUiModel.StatusUiModel(
                text = "Виконане",
                background = Color(0xFFC6EB88)
            )
        }
    }
}