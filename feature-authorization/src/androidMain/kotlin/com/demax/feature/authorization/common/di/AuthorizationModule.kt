package com.demax.feature.authorization.common.di

import com.demax.feature.authorization.common.data.AuthorizationRepositoryImpl
import com.demax.feature.authorization.common.domain.AuthorizationRepository
import com.demax.feature.authorization.login.LoginViewModel
import com.demax.feature.authorization.passwordReset.PasswordResetViewModel
import com.demax.feature.authorization.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureAuthorizationModule() = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::PasswordResetViewModel)
    factoryOf(::AuthorizationRepositoryImpl) { bind<AuthorizationRepository>() }
}
