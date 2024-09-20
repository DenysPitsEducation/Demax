package com.demax.feature.help.history.di

import com.demax.feature.help.history.HelpHistoryViewModel
import com.demax.feature.help.history.data.HelpHistoryRepositoryImpl
import com.demax.feature.help.history.domain.HelpHistoryRepository
import com.demax.feature.help.history.mapper.HelpHistoryUiMapper
import com.demax.feature.help.history.navigation.HelpHistoryPayload
import com.google.rpc.Help
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureHelpHistoryModule() = module {
    viewModel<HelpHistoryViewModel> { (payload: HelpHistoryPayload) ->
        HelpHistoryViewModel(payload = payload, repository = get())
    }
    factoryOf(::HelpHistoryUiMapper)
    factoryOf(::HelpHistoryRepositoryImpl) { bind<HelpHistoryRepository>() }
}
