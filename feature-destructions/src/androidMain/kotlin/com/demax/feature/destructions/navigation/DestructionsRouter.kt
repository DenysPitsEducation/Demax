package com.demax.feature.destructions.navigation

import androidx.navigation.NavController

interface DestructionsRouter {
    fun openDestructionEditScreen(navController: NavController)
    fun openDestructionDetails(navController: NavController, id: String)
}
