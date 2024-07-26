package com.demax.feature.responses.di

import com.demax.feature.responses.ResponsesViewModel
import com.demax.feature.responses.data.ResponsesRepositoryImpl
import com.demax.feature.responses.domain.ResponsesRepository
import com.demax.feature.responses.mapper.ResponsesUiMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureResponsesModule() = module {
    viewModelOf(::ResponsesViewModel)
    factoryOf(::ResponsesUiMapper)
    factoryOf(::ResponsesRepositoryImpl) { bind<ResponsesRepository>() }
}
