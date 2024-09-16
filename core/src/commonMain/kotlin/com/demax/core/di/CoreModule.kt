package com.demax.core.di

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun coreModule() = module {
    nativeDependencies()

    factoryOf(::BuildingTypeDomainMapper)
}

internal expect fun Module.nativeDependencies()
