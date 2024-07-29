package com.demax.android.di

import com.demax.feature.authorization.common.di.featureAuthorizationModule
import com.demax.feature.destruction.details.di.featureDestructionDetailsModule
import com.demax.feature.destructions.di.featureDestructionsModule
import com.demax.feature.resources.di.featureResourcesModule
import com.demax.feature.responses.di.featureResponsesModule

fun getAllKoinModules() = listOf(
    appModule(),
    featureAuthorizationModule(),
    featureDestructionDetailsModule(),
    featureDestructionsModule(),
    featureResourcesModule(),
    featureResponsesModule(),
)