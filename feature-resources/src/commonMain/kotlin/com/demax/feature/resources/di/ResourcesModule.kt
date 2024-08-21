package com.demax.feature.resources.di

import com.demax.feature.resources.data.ResourcesRepositoryImpl
import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.core.data.mapper.ResourceCategoryDomainMapper
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


fun featureResourcesModule() = module {
    nativeDependencies()

    factoryOf(::ResourcesRepositoryImpl) { bind<ResourcesRepository>() }
    factoryOf(::ResourceCategoryDomainMapper)
}

internal expect fun Module.nativeDependencies()
