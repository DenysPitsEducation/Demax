package com.demax.android

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.demax.core.domain.repository.UserRepository
import com.demax.feature.destruction.details.composable.DestructionDetailsScreen
import com.demax.feature.destruction.details.navigation.DestructionDetailsPayload
import com.demax.feature.destruction.edit.composable.DestructionEditScreen
import com.demax.feature.destruction.edit.navigation.DestructionEditPayload
import com.demax.feature.destructions.navigation.DestructionsPayload
import com.demax.feature.destructions.composable.DestructionsScreen
import com.demax.feature.help.history.composable.HelpHistoryScreen
import com.demax.feature.help.history.navigation.HelpHistoryPayload
import com.demax.feature.profile.composable.ProfileScreen
import com.demax.feature.profile.navigation.ProfilePayload
import com.demax.feature.resource.details.composable.ResourceDetailsScreen
import com.demax.feature.resource.details.navigation.ResourceDetailsPayload
import com.demax.feature.resource.edit.composable.ResourceEditScreen
import com.demax.feature.resource.edit.navigation.ResourceEditPayload
import com.demax.feature.resources.composable.ResourcesScreen
import com.demax.feature.resources.navigation.ResourcesPayload
import com.demax.feature.responses.composable.ResponsesScreen
import com.demax.feature.responses.navigation.ResponsesPayload
import dev.gitlive.firebase.Firebase
import org.koin.compose.koinInject

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val userRepository: UserRepository = koinInject()
    val user by userRepository.getUserFlow().collectAsStateWithLifecycle(initialValue = null)
    val showResponses = user?.isAdministrator == true
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            NavigationBar {
                var selectedItem by remember { mutableIntStateOf(0) }
                val items = listOfNotNull(
                    NavigationBarItemModel(
                        text = "Руйнування",
                        icon = Icons.Default.Search,
                        payload = DestructionsPayload
                    ),
                    NavigationBarItemModel(
                        text = "Ресурси",
                        icon = Icons.Default.Inventory,
                        payload = ResourcesPayload
                    ),
                    NavigationBarItemModel(
                        text = "Відгуки",
                        icon = Icons.Default.Star,
                        payload = ResponsesPayload
                    ).takeIf { showResponses },
                    NavigationBarItemModel(
                        text = "Профіль",
                        icon = Icons.Default.AccountCircle,
                        payload = ProfilePayload(profileId = null)
                    ),
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text
                            )
                        },
                        label = { Text(item.text) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.payload) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DestructionsPayload,
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
        ) {
            composable<DestructionDetailsPayload> { backStackEntry ->
                DestructionDetailsScreen(
                    navController = navController,
                    payload = backStackEntry.toRoute(),
                )
            }
            composable<DestructionEditPayload> { backStackEntry ->
                DestructionEditScreen(
                    navController = navController,
                    payload = backStackEntry.toRoute(),
                )
            }
            composable<DestructionsPayload> {
                DestructionsScreen(navController)
            }
            composable<HelpHistoryPayload> { backStackEntry ->
                HelpHistoryScreen(
                    navController = navController,
                    payload = backStackEntry.toRoute(),
                )
            }
            composable<ProfilePayload> { backStackEntry ->
                ProfileScreen(
                    navController = navController,
                    payload = backStackEntry.toRoute(),
                )
            }
            composable<ResourceDetailsPayload> { backStackEntry ->
                ResourceDetailsScreen(
                    navController = navController,
                    payload = backStackEntry.toRoute(),
                )
            }
            composable<ResourceEditPayload> { backStackEntry ->
                ResourceEditScreen(
                    navController = navController,
                    payload = backStackEntry.toRoute(),
                )
            }
            composable<ResourcesPayload> {
                ResourcesScreen(navController)
            }
            composable<ResponsesPayload> {
                ResponsesScreen(navController)
            }
        }
    }
}

private data class NavigationBarItemModel(
    val text: String,
    val icon: ImageVector,
    val payload: Any,
)
