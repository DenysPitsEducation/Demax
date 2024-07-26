package com.demax.android.di

import com.demax.feature.authorization.common.di.featureAuthorizationModule
import com.demax.feature.destructions.di.featureDestructionsModule
import com.demax.feature.resources.di.featureResourcesModule

fun getAllKoinModules() = listOf(
    appModule(),
    featureAuthorizationModule(),
    featureDestructionsModule(),
    featureResourcesModule(),
)