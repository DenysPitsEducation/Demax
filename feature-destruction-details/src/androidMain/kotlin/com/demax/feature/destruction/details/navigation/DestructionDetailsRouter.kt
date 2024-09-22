package com.demax.feature.destruction.details.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.demax.core.navigation.ResourceHelpPayload

interface DestructionDetailsRouter {
    fun openResourceEditScreen(navController: NavHostController)

    fun openResourceDetails(navController: NavController, id: String)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ResourceHelpBottomSheet(
        payload: ResourceHelpPayload,
        dismissAction: () -> Unit,
        bottomSheetState: SheetState,
    )
}
