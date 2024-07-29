package com.demax.feature.destruction.details.di

import com.demax.feature.destruction.details.DestructionDetailsViewModel
import com.demax.feature.destruction.details.data.DestructionDetailsRepositoryImpl
import com.demax.feature.destruction.details.domain.DestructionDetailsRepository
import com.demax.feature.destruction.details.mapper.DestructionDetailsUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureDestructionDetailsModule() = module {
    viewModelOf(::DestructionDetailsViewModel)
    factoryOf(::DestructionDetailsUiMapper)
    factoryOf(::DestructionDetailsRepositoryImpl) { bind<DestructionDetailsRepository>() }
}
