package com.demax.feature.resources.di

import com.demax.feature.resources.ResourcesViewModel
import com.demax.feature.resources.mapper.ResourcesUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf

internal actual fun Module.nativeDependencies() {
    viewModelOf(::ResourcesViewModel)
    factoryOf(::ResourcesUiMapper)
}