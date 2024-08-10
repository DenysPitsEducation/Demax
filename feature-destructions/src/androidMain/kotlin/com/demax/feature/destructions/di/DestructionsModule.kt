package com.demax.feature.destructions.di

import com.demax.feature.destructions.DestructionsViewModel
import com.demax.feature.destructions.data.DestructionsRepositoryImpl
import com.demax.feature.destructions.domain.DestructionsRepository
import com.demax.feature.destructions.mapper.BuildingTypeUiMapper
import com.demax.feature.destructions.mapper.DestructionsUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureDestructionsModule() = module {
    viewModelOf(::DestructionsViewModel)
    factoryOf(::DestructionsUiMapper)
    factoryOf(::BuildingTypeUiMapper)
    factoryOf(::DestructionsRepositoryImpl) { bind<DestructionsRepository>() }
}
