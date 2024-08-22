package com.demax.feature.resource.edit.di

import com.demax.feature.resource.edit.ResourceEditViewModel
import com.demax.feature.resource.edit.data.ResourceEditRepositoryImpl
import com.demax.feature.resource.edit.domain.ResourceEditRepository
import com.demax.feature.resource.edit.mapper.ResourceEditUiMapper
import com.demax.feature.resource.edit.navigation.ResourceEditPayload
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResourceEditModule() = module {
    viewModel<ResourceEditViewModel> { (payload: ResourceEditPayload) ->
        ResourceEditViewModel(payload = payload, repository = get())
    }
    factoryOf(::ResourceEditUiMapper)
    factoryOf(::ResourceEditRepositoryImpl) { bind<ResourceEditRepository>() }
}
