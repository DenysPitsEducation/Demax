package com.demax.feature.resource.edit.mvi

import com.demax.core.domain.model.ResourceCategoryDomainModel
import dev.gitlive.firebase.storage.File

sealed interface ResourceEditIntent {
    data class ImageSelected(val file: File) : ResourceEditIntent
    data class NameChanged(val value: String) : ResourceEditIntent
    data class CategoryValueClicked(val category: ResourceCategoryDomainModel) : ResourceEditIntent
    data class TotalAmountChanged(val value: String) : ResourceEditIntent
    data class CurrentAmountChanged(val value: String) : ResourceEditIntent
    data class DescriptionChanged(val value: String) : ResourceEditIntent
    data class DestructionValueClicked(val destructionId: String) : ResourceEditIntent
    data object SaveButtonClicked : ResourceEditIntent
}