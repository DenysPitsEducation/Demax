package com.demax.feature.authorization.common

import androidx.navigation.NavController

interface AuthorizationRouter {
    fun openMainScreen(navController: NavController)
    fun openPasswordResetScreen(navController: NavController)
    fun openRegistrationScreen(navController: NavController)
    fun openLoginScreen(navController: NavController)
}