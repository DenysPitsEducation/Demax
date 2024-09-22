package com.demax.feature.resource.details.di

import com.demax.feature.resource.details.ResourceDetailsViewModel
import com.demax.feature.resource.details.data.ResourceDetailsRepositoryImpl
import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.mapper.ResourceDetailsUiMapper
import com.demax.feature.resource.details.navigation.ResourceDetailsPayload
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResourceDetailsModule() = module {
    viewModel<ResourceDetailsViewModel> { (payload: ResourceDetailsPayload) ->
        ResourceDetailsViewModel(payload = payload, repository = get(), userRepository = get())
    }
    factoryOf(::ResourceDetailsUiMapper)
    factoryOf(::ResourceDetailsRepositoryImpl) { bind<ResourceDetailsRepository>() }
}
