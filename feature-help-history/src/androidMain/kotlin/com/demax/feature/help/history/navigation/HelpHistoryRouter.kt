package com.demax.feature.help.history.navigation

import androidx.navigation.NavController

interface HelpHistoryRouter {
    fun openProfile(navController: NavController, id: String)
    fun openDestructionDetails(navController: NavController, id: String)
    fun openResourceDetails(navController: NavController, id: String)
}
