package com.demax.feature.resource.edit.domain.model

import com.demax.core.domain.model.ResourceCategoryDomainModel
import dev.gitlive.firebase.storage.File

data class ResourceEditDomainModel(
    val id: String,
    val imageUrl: String?,
    val imageFile: File?,
    val name: String?,
    val category: ResourceCategoryDomainModel?,
    val totalAmount: Int?,
    val currentAmount: Int?,
    val description: String?,
    val availableDestructions: List<DestructionDomainModel>,
    val selectedDestruction: DestructionDomainModel?,
)
