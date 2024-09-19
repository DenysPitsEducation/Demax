package com.demax.feature.profile.di

import com.demax.feature.profile.ProfileViewModel
import com.demax.feature.profile.data.ProfileRepositoryImpl
import com.demax.feature.profile.domain.ProfileRepository
import com.demax.feature.profile.mapper.ProfileUiMapper
import com.demax.feature.profile.navigation.ProfilePayload
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureProfileModule() = module {
    viewModel<ProfileViewModel> { (payload: ProfilePayload) ->
        ProfileViewModel(payload = payload, repository = get())
    }
    factoryOf(::ProfileUiMapper)
    factoryOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }
}
