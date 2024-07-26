package com.demax.feature.resources.di

import com.demax.feature.resources.ResourcesViewModel
import com.demax.feature.resources.data.ResourcesRepositoryImpl
import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.feature.resources.mapper.ResourcesUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResourcesModule() = module {
    viewModelOf(::ResourcesViewModel)
    factoryOf(::ResourcesUiMapper)
    factoryOf(::ResourcesRepositoryImpl) { bind<ResourcesRepository>() }
}
