package com.demax.feature.destruction.edit.di

import com.demax.feature.destruction.edit.DestructionEditViewModel
import com.demax.feature.destruction.edit.data.DestructionEditRepositoryImpl
import com.demax.feature.destruction.edit.domain.DestructionEditRepository
import com.demax.feature.destruction.edit.mapper.DestructionEditUiMapper
import com.demax.feature.destruction.edit.navigation.DestructionEditPayload
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureDestructionEditModule() = module {
    viewModel<DestructionEditViewModel> { (payload: DestructionEditPayload) ->
        DestructionEditViewModel(payload = payload, repository = get())
    }
    factoryOf(::DestructionEditUiMapper)
    factoryOf(::DestructionEditRepositoryImpl) { bind<DestructionEditRepository>() }
}
