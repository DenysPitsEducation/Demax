package com.demax.feature.resource.help.mapper

import com.demax.feature.resource.help.domain.model.ResourceHelpBottomSheetDomainModel
import com.demax.feature.resource.help.model.ResourceHelpBottomSheetUiModel
import com.demax.feature.resource.help.model.ResourceNeedBottomSheetUiModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern

class ResourceHelpUiMapper {
    fun mapToUiModel(model: ResourceHelpBottomSheetDomainModel): ResourceHelpBottomSheetUiModel = model.run {
        val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        return ResourceHelpBottomSheetUiModel(
            dateInputText = selectedDate?.let { formatter.format(it) },
            needs = needs.map { need ->
                ResourceNeedBottomSheetUiModel(
                    id = need.id,
                    title = need.title,
                    quantity = need.quantityText,
                    isSelected = need.isSelected,
                )
            },
            isButtonEnabled = selectedDate != null && needs.any { it.isSelected },
        )
    }
}