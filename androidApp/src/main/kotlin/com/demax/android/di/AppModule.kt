package com.demax.android.di

import androidx.navigation.NavController
import com.demax.android.MainPayload
import com.demax.feature.authorization.common.AuthorizationRouter
import com.demax.feature.authorization.login.LoginPayload
import com.demax.feature.authorization.passwordReset.PasswordResetPayload
import com.demax.feature.authorization.registration.RegistrationPayload
import com.demax.feature.destruction.details.navigation.DestructionDetailsPayload
import com.demax.feature.destruction.details.navigation.DestructionDetailsRouter
import com.demax.feature.destructions.navigation.DestructionsRouter
import com.demax.feature.resources.navigation.ResourcesRouter
import com.demax.feature.responses.navigation.ResponsesRouter
import org.koin.dsl.module

fun appModule() = module {
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
        }
    }
    factory<DestructionsRouter> {
        object : DestructionsRouter {
            override fun openDestructionDetails(navController: NavController, id: Long) {
                navController.navigate(DestructionDetailsPayload(id))
            }
        }
    }
    factory<ResourcesRouter> {
        object : ResourcesRouter {
        }
    }
    factory<ResponsesRouter> {
        object : ResponsesRouter {
        }
    }
}