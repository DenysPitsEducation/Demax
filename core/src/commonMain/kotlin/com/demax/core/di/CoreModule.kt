package com.demax.core.di

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.mapper.StatusDomainMapper
import com.demax.core.domain.model.BuildingTypeDomainModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun coreModule() = module {
    nativeDependencies()

    factoryOf(::BuildingTypeDomainMapper)
    factoryOf(::StatusDomainMapper)
}

internal expect fun Module.nativeDependencies()
