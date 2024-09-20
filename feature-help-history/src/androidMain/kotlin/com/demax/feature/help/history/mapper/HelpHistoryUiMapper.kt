package com.demax.feature.help.history.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.ui.mapper.ResourceCategoryUiMapper
import com.demax.feature.help.history.domain.model.ProfileDomainModel
import com.demax.feature.help.history.domain.model.HelpDomainModel
import com.demax.feature.help.history.domain.model.HelpTypeDomainModel
import com.demax.feature.help.history.model.DestructionUiModel
import com.demax.feature.help.history.model.ProfileUiModel
import com.demax.feature.help.history.model.ResourceUiModel
import com.demax.feature.help.history.model.ResponseTypeUiModel
import com.demax.feature.help.history.model.HelpUiModel
import com.demax.feature.help.history.model.HelpHistoryUiModel
import com.demax.feature.help.history.model.StatusUiModel
import com.demax.feature.help.history.mvi.HelpHistoryState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

internal class HelpHistoryUiMapper(
    private val resourceCategoryMapper: ResourceCategoryUiMapper,
) {

    fun mapToUiModel(state: HelpHistoryState): HelpHistoryUiModel = state.run {
        return HelpHistoryUiModel(
            helpUiModels = responses.map { it.toUiModel() }
        )
    }

    private fun HelpDomainModel.toUiModel(): HelpUiModel {
        return HelpUiModel(
            id = id,
            type = type.toUiModel(),
            status = status.toUiModel(),
        )
    }

    private fun HelpTypeDomainModel.toUiModel(): ResponseTypeUiModel {
        return when (this) {
            is HelpTypeDomainModel.Resource -> {
                ResponseTypeUiModel.Resource(
                    resources = resources.map { resource ->
                        ResourceUiModel(
                            id = resource.id,
                            imageUrl = resource.imageUrl,
                            category = resourceCategoryMapper.mapToUiModel(resource.category),
                            name = resource.name,
                            quantity = resource.quantity,
                        )
                    }
                )
            }

            is HelpTypeDomainModel.Volunteer -> {
                val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
                ResponseTypeUiModel.Volunteer(
                    destruction = DestructionUiModel(
                        id = destruction.id,
                        imageUrl = destruction.imageUrl,
                        destructionDate = formatter.format(destruction.destructionDate),
                        address = destruction.address,
                    ),
                    specializations = specializations,
                )
            }
        }
    }

    private fun HelpDomainModel.StatusDomainModel.toUiModel(): StatusUiModel {
        return when (this) {
            HelpDomainModel.StatusDomainModel.IDLE -> StatusUiModel(
                text = "Очікує розгляду",
                background = Color(0xFFF2F487)
            )
            HelpDomainModel.StatusDomainModel.APPROVED -> StatusUiModel(
                text = "Схвалено",
                background = Color(0xFFC6EB88)
            )
            HelpDomainModel.StatusDomainModel.REJECTED -> StatusUiModel(
                text = "Відхилено",
                background = Color(0xFFE23538)
            )
        }
    }
}