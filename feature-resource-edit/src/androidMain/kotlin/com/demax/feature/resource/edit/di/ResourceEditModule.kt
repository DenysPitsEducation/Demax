package com.demax.feature.resource.edit.di

import com.demax.feature.resource.edit.ResourceEditViewModel
import com.demax.feature.resource.edit.data.ResourceEditRepositoryImpl
import com.demax.feature.resource.edit.domain.ResourceEditRepository
import com.demax.feature.resource.edit.mapper.ResourceEditUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResourceEditModule() = module {
    viewModelOf(::ResourceEditViewModel)
    factoryOf(::ResourceEditUiMapper)
    factoryOf(::ResourceEditRepositoryImpl) { bind<ResourceEditRepository>() }
}
