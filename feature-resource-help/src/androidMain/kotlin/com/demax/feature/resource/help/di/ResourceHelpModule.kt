package com.demax.feature.resource.help.di

import com.demax.feature.resource.help.ResourceHelpViewModel
import com.demax.feature.resource.help.data.repository.ResourceHelpRepositoryImpl
import com.demax.feature.resource.help.domain.repository.ResourceHelpRepository
import com.demax.feature.resource.help.mapper.ResourceHelpUiMapper
import com.demax.core.navigation.ResourceHelpPayload
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResourceHelpModule() = module {
    viewModel<ResourceHelpViewModel> { (payload: ResourceHelpPayload) ->
        ResourceHelpViewModel(payload = payload, repository = get())
    }
    factoryOf(::ResourceHelpUiMapper)
    factoryOf(::ResourceHelpRepositoryImpl) { bind<ResourceHelpRepository>() }
}
