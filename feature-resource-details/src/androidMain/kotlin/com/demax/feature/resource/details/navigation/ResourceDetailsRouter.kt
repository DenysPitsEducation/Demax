package com.demax.feature.resource.details.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.demax.core.navigation.ResourceHelpPayload

interface ResourceDetailsRouter {
    fun openResourceEditScreen(navController: NavController, resourceId: String)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ResourceHelpBottomSheet(
        payload: ResourceHelpPayload,
        dismissAction: () -> Unit,
        bottomSheetState: SheetState,
    )
}
