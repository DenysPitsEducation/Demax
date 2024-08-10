package com.demax.feature.destructions.navigation

import androidx.navigation.NavController

interface DestructionsRouter {
    fun openDestructionDetails(navController: NavController, id: String)
}
