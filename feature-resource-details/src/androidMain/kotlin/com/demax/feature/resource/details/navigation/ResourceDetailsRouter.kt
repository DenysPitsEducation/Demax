package com.demax.feature.resource.details.navigation

import androidx.navigation.NavController

interface ResourceDetailsRouter {
    fun openResourceEditScreen(navController: NavController, resourceId: String)
}
