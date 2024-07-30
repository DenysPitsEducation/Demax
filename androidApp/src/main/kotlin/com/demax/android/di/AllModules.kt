package com.demax.android.di

import com.demax.feature.authorization.common.di.featureAuthorizationModule
import com.demax.feature.destruction.details.di.featureDestructionDetailsModule
import com.demax.feature.destructions.di.featureDestructionsModule
import com.demax.feature.profile.di.featureProfileModule
import com.demax.feature.resource.details.di.featureResourceDetailsModule
import com.demax.feature.resources.di.featureResourcesModule
import com.demax.feature.responses.di.featureResponsesModule

fun getAllKoinModules() = listOf(
    appModule(),
    featureAuthorizationModule(),
    featureDestructionDetailsModule(),
    featureDestructionsModule(),
    featureProfileModule(),
    featureResourceDetailsModule(),
    featureResourcesModule(),
    featureResponsesModule(),
)