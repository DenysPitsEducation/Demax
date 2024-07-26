package com.demax.feature.authorization.passwordReset

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import org.koin.compose.koinInject

private typealias OnUserInteraction = (PasswordResetIntent) -> Unit

@Composable
fun PasswordResetScreen() {
    val viewModel: PasswordResetViewModel = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is PasswordResetSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    PasswordResetContent(state = state, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
fun PasswordResetContent(state: PasswordResetState, onUserInteraction: OnUserInteraction) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Забули пароль?",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                onUserInteraction(PasswordResetIntent.EmailInputChanged(it))
            },
            placeholder = {
                Text(text = "Електронна пошта")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "*На вказану електронну пошту Вам надійде лист для зміни пароля",
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                onUserInteraction(PasswordResetIntent.SendClicked)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Надіслати")
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
@Preview
private fun PasswordResetComposablePreview() {
    PreviewContainer {
        PasswordResetContent(
            state = PasswordResetState(email = ""),
            onUserInteraction = {},
        )
    }
}