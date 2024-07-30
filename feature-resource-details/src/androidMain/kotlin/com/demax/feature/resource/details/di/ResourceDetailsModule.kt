package com.demax.feature.resource.details.di

import com.demax.feature.resource.details.ResourceDetailsViewModel
import com.demax.feature.resource.details.data.ResourceDetailsRepositoryImpl
import com.demax.feature.resource.details.domain.ResourceDetailsRepository
import com.demax.feature.resource.details.mapper.ResourceDetailsUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResourceDetailsModule() = module {
    viewModelOf(::ResourceDetailsViewModel)
    factoryOf(::ResourceDetailsUiMapper)
    factoryOf(::ResourceDetailsRepositoryImpl) { bind<ResourceDetailsRepository>() }
}
