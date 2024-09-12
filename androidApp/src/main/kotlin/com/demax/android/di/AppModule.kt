package com.demax.android.di

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.demax.android.AppInitializer
import com.demax.android.MainPayload
import com.demax.feature.authorization.common.AuthorizationRouter
import com.demax.feature.authorization.login.LoginPayload
import com.demax.feature.authorization.passwordReset.PasswordResetPayload
import com.demax.feature.authorization.registration.RegistrationPayload
import com.demax.feature.destruction.details.navigation.DestructionDetailsPayload
import com.demax.feature.destruction.details.navigation.DestructionDetailsRouter
import com.demax.feature.destruction.edit.navigation.DestructionEditPayload
import com.demax.feature.destruction.edit.navigation.DestructionEditRouter
import com.demax.feature.destructions.navigation.DestructionsRouter
import com.demax.feature.profile.navigation.ProfileRouter
import com.demax.feature.resource.details.navigation.ResourceDetailsPayload
import com.demax.feature.resource.details.navigation.ResourceDetailsRouter
import com.demax.feature.resource.edit.navigation.ResourceEditPayload
import com.demax.feature.resource.edit.navigation.ResourceEditRouter
import com.demax.feature.resources.navigation.ResourcesRouter
import com.demax.feature.responses.navigation.ResponsesRouter
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun appModule() = module {
    factoryOf(::AppInitializer)

    factory<AuthorizationRouter> {
        object : AuthorizationRouter {
            override fun openMainScreen(navController: NavController) {
                navController.navigate(MainPayload)
            }

            override fun openPasswordResetScreen(navController: NavController) {
                navController.navigate(PasswordResetPayload)
            }

            override fun openRegistrationScreen(navController: NavController) {
                navController.navigate(RegistrationPayload)
            }

            override fun openLoginScreen(navController: NavController) {
                navController.navigate(LoginPayload)
            }
        }
    }
    factory<DestructionDetailsRouter> {
        object : DestructionDetailsRouter {
            override fun openResourceEditScreen(navController: NavHostController) {
                navController.navigate(ResourceEditPayload(resourceId = null))
            }
        }
    }
    factory<DestructionEditRouter> {
        object : DestructionEditRouter {
        }
    }
    factory<DestructionsRouter> {
        object : DestructionsRouter {
            override fun openDestructionEditScreen(navController: NavController) {
                navController.navigate(DestructionEditPayload(destructionId = null))
            }

            override fun openDestructionDetails(navController: NavController, id: String) {
                navController.navigate(DestructionDetailsPayload(id))
            }
        }
    }
    factory<ProfileRouter> {
        object : ProfileRouter {
        }
    }
    factory<ResourceDetailsRouter> {
        object : ResourceDetailsRouter {
            override fun openResourceEditScreen(navController: NavController, resourceId: String) {
                navController.navigate(ResourceEditPayload(resourceId))
            }
        }
    }
    factory<ResourceEditRouter> {
        object : ResourceEditRouter {
        }
    }
    factory<ResourcesRouter> {
        object : ResourcesRouter {
            override fun openResourceDetails(navController: NavController, id: String) {
                navController.navigate(ResourceDetailsPayload(id))
            }

            override fun openResourceEdit(navController: NavController) {
                navController.navigate(ResourceEditPayload(resourceId = null))
            }
        }
    }
    factory<ResponsesRouter> {
        object : ResponsesRouter {
        }
    }
}