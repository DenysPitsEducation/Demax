package com.demax.feature.destruction.edit.mvi

import com.demax.feature.destruction.edit.domain.model.DestructionEditDomainModel
import com.demax.feature.destruction.edit.domain.model.NeedsDomainModel

data class DestructionEditState(
    val domainModel: DestructionEditDomainModel?,
)
