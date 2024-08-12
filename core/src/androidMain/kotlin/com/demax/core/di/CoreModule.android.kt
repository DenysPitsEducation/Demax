package com.demax.core.di

import com.demax.core.ui.mapper.BuildingTypeUiMapper
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf

internal actual fun Module.nativeDependencies() {
    factoryOf(::BuildingTypeUiMapper)
}