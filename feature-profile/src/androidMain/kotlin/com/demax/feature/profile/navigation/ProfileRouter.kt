package com.demax.feature.profile.navigation

import androidx.navigation.NavController

interface ProfileRouter {
    fun openHelpHistory(navController: NavController, profileId: String)
    fun openAuthorizationFlow(navController: NavController)
}
