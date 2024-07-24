package com.demax.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demax.feature.destructions.DestructionsPayload
import com.demax.feature.destructions.DestructionsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                var selectedItem by remember { mutableIntStateOf(0) }
                val items = listOf(
                    NavigationBarItemModel("Руйнування", Icons.Filled.Search),
                    NavigationBarItemModel("Відгуки", Icons.Filled.Star),
                    NavigationBarItemModel("Профіль", Icons.Filled.AccountCircle),
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
                            /*navController.navigate(navigationItem.route) {
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
                            }*/
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController, DestructionsPayload) {
            composable<DestructionsPayload> {
                DestructionsScreen(navController)
            }
        }
    }
}