package com.demax.feature.authorization.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.authorization.common.AuthorizationRouter
import org.koin.compose.koinInject

private typealias OnUserInteraction = (LoginIntent) -> Unit

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = koinInject()
    val router: AuthorizationRouter = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.OpenMainScreen -> router.openMainScreen(navController)
            is LoginSideEffect.OpenPasswordResetScreen -> router.openPasswordResetScreen(navController)
            is LoginSideEffect.OpenRegistrationScreen -> router.openRegistrationScreen(navController)
            is LoginSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    LoginContent(state = state, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
private fun LoginContent(
    state: LoginState,
    onUserInteraction: OnUserInteraction,
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "З поверненням!",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                onUserInteraction(LoginIntent.EmailInputChanged(it))
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
                onUserInteraction(LoginIntent.PasswordInputChanged(it))
            },
            placeholder = {
                Text(text = "Пароль")
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Забули пароль?",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onUserInteraction(LoginIntent.OpenPasswordResetScreen)
                },
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                onUserInteraction(LoginIntent.LoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Увійти")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Створити обліковий запис",
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onUserInteraction(LoginIntent.CreateAccountClicked)
                },
        )
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
@Preview
private fun LoginContentPreview() {
    PreviewContainer {
        LoginContent(
            state = LoginState(email = "", password = ""),
            onUserInteraction = {},
        )
    }
}
