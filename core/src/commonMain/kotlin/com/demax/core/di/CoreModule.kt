package com.demax.core.di

import com.demax.core.data.mapper.BuildingTypeDomainMapper
import com.demax.core.data.repository.UserRepositoryImpl
import com.demax.core.domain.repository.UserRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun coreModule() = module {
    nativeDependencies()

    factoryOf(::BuildingTypeDomainMapper)
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
}

internal expect fun Module.nativeDependencies()
