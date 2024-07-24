package com.demax.android.di

import com.demax.feature.authorization.common.di.featureAuthorizationModule
import com.demax.feature.destructions.di.featureDestructionsModule

fun getAllKoinModules() = listOf(
    appModule(),
    featureAuthorizationModule(),
    featureDestructionsModule(),
)