package com.demax.feature.resource.edit.mapper

import com.demax.core.domain.model.ResourceCategoryDomainModel
import com.demax.core.ui.mapper.ResourceCategoryUiMapper
import com.demax.feature.resource.edit.domain.model.ResourceEditDomainModel
import com.demax.feature.resource.edit.model.CategoryUiModel
import com.demax.feature.resource.edit.model.DestructionUiModel
import com.demax.feature.resource.edit.model.ResourceEditUiModel
import com.demax.feature.resource.edit.mvi.ResourceEditState

internal class ResourceEditUiMapper(
    private val categoryUiMapper: ResourceCategoryUiMapper,
) {

    fun mapToUiModel(state: ResourceEditState): ResourceEditUiModel? = state.run {
        return state.domainModel?.toUiModel()
    }

    private fun ResourceEditDomainModel.toUiModel(): ResourceEditUiModel {
        return ResourceEditUiModel(
            image = imageUrl ?: imageFile?.uri,
            name = name.orEmpty(),
            category = category?.let { categoryUiMapper.mapToUiModel(it) }.orEmpty(),
            dropDownCategories = ResourceCategoryDomainModel.entries.map {
                val title = categoryUiMapper.mapToUiModel(it)
                CategoryUiModel(it, title)
            },
            totalAmount = totalAmount?.toString().orEmpty(),
            currentAmount = currentAmount?.toString().orEmpty(),
            description = description.orEmpty(),
            destruction = selectedDestruction?.address.orEmpty(),
            dropDownDestructions = availableDestructions.map {
                DestructionUiModel(id = it.id, title = it.address)
            }
        )
    }
}