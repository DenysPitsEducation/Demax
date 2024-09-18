package com.demax.android.di

import com.demax.core.di.coreModule
import com.demax.feature.authorization.common.di.featureAuthorizationModule
import com.demax.feature.destruction.details.di.featureDestructionDetailsModule
import com.demax.feature.destruction.edit.di.featureDestructionEditModule
import com.demax.feature.destructions.di.featureDestructionsModule
import com.demax.feature.profile.di.featureProfileModule
import com.demax.feature.resource.details.di.featureResourceDetailsModule
import com.demax.feature.resource.edit.di.featureResourceEditModule
import com.demax.feature.resource.help.di.featureResourceHelpModule
import com.demax.feature.resources.di.featureResourcesModule
import com.demax.feature.responses.di.featureResponsesModule

fun getAllKoinModules() = listOf(
    appModule(),
    coreModule(),
    featureAuthorizationModule(),
    featureDestructionDetailsModule(),
    featureDestructionEditModule(),
    featureDestructionsModule(),
    featureProfileModule(),
    featureResourceDetailsModule(),
    featureResourceEditModule(),
    featureResourceHelpModule(),
    featureResourcesModule(),
    featureResponsesModule(),
)