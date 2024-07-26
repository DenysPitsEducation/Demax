package com.demax.feature.authorization.registration

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.authorization.common.AuthorizationRouter
import com.demax.feature.authorization.passwordReset.PasswordResetIntent
import org.koin.compose.koinInject

private typealias OnUserInteraction = (RegistrationIntent) -> Unit

@Composable
fun RegistrationScreen(navController: NavController) {
    val viewModel: RegistrationViewModel = koinInject()
    val router: AuthorizationRouter = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is RegistrationSideEffect.OpenLoginScreen -> router.openLoginScreen(navController)
            is RegistrationSideEffect.OpenMainScreen -> router.openMainScreen(navController)
            is RegistrationSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    RegistrationContent(state = state, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
private fun RegistrationContent(state: RegistrationState, onUserInteraction: OnUserInteraction) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Створення облікового запису",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                onUserInteraction(RegistrationIntent.EmailInputChanged(it))
            },
            placeholder = {
                Text(text = "Електронна пошта")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                onUserInteraction(RegistrationIntent.PasswordInputChanged(it))
            },
            placeholder = {
                Text(text = "Пароль")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = state.passwordConfirmation,
            onValueChange = {
                onUserInteraction(RegistrationIntent.PasswordConfirmationInputChanged(it))
            },
            placeholder = {
                Text(text = "Підтвердження паролю")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                onUserInteraction(RegistrationIntent.CreateAccountClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Створити")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Я вже маю обліковий запис",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Увійти",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onUserInteraction(RegistrationIntent.LoginClicked)
                }
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
@Preview
private fun LoginComposablePreview() {
    PreviewContainer {
        RegistrationContent(
            state = RegistrationState(email = "", password = "", passwordConfirmation = ""),
            onUserInteraction = {},
        )
    }
}
