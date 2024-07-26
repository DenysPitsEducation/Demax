package com.demax.feature.destructions.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.utils.formatFractionalPart
import com.demax.feature.destructions.domain.model.DestructionDomainModel
import com.demax.feature.destructions.model.DestructionItemUiModel
import com.demax.feature.destructions.model.DestructionsUiModel
import com.demax.feature.destructions.mvi.DestructionsState
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class DestructionsUiMapper {

    fun mapToUiModel(state: DestructionsState): DestructionsUiModel = state.run {
        return DestructionsUiModel(
            searchInput = searchInput,
            showAddDestructionButton = isAdministrator,
            destructionItemUiModels = destructions.map { it.toUiModel() }
        )
    }

    private fun DestructionDomainModel.toUiModel(): DestructionItemUiModel {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        return DestructionItemUiModel(
            id = id,
            imageUrl = imageUrl,
            buildingType = buildingType,
            address = address,
            destructionDate = formatter.format(destructionDate.toJavaLocalDate()),
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
        return when(this) {
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