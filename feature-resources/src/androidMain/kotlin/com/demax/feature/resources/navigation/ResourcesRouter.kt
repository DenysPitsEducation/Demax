package com.demax.feature.resources.navigation

import androidx.navigation.NavController

interface ResourcesRouter {
    fun openResourceDetails(navController: NavController, id: Long)
}
