package com.demax.feature.responses.navigation

import androidx.navigation.NavController

interface ResponsesRouter {
    fun openProfile(navController: NavController, id: String)
    fun openDestructionDetails(navController: NavController, id: String)
    fun openResourceDetails(navController: NavController, id: String)
}
