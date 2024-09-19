package com.demax.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.demax.core.ui.DemaxTheme
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.feature.authorization.common.AuthorizationPayload
import com.demax.feature.authorization.login.LoginPayload
import com.demax.feature.authorization.login.LoginScreen
import com.demax.feature.authorization.passwordReset.PasswordResetScreen
import com.demax.feature.authorization.passwordReset.PasswordResetPayload
import com.demax.feature.authorization.registration.RegistrationScreen
import com.demax.feature.authorization.registration.RegistrationPayload
import com.google.rpc.context.AttributeContext.Auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DemaxTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                CompositionLocalProvider(
                    LocalSnackbarHostState provides snackbarHostState,
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        },
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = MainPayload,
                            modifier = Modifier
                                .consumeWindowInsets(innerPadding)
                                .padding(innerPadding)
                        ) {
                            navigation<AuthorizationPayload>(startDestination = LoginPayload) {
                                composable<LoginPayload> {
                                    LoginScreen(navController)
                                }
                                composable<RegistrationPayload> {
                                    RegistrationScreen(navController)
                                }
                                composable<PasswordResetPayload> {
                                    PasswordResetScreen()
                                }
                            }
                            composable<MainPayload> {
                                MainScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
