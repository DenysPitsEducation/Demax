package com.demax.feature.destructions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import org.koin.compose.koinInject

private typealias OnUserInteraction = (DestructionsIntent) -> Unit

@Composable
fun DestructionsScreen(navController: NavHostController) {
    val viewModel: DestructionsViewModel = koinInject()
    val router: DestructionsRouter = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        /*when (sideEffect) {
            is DestructionsSideEffect.OpenMainScreen -> router.openMainScreen(navController)
            is DestructionsSideEffect.OpenPasswordResetScreen -> router.openPasswordResetScreen(navController)
            is DestructionsSideEffect.OpenRegistrationScreen -> router.openRegistrationScreen(navController)
            is DestructionsSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }*/
    }

    DestructionsContent(state = state, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
private fun DestructionsContent(
    state: DestructionsState,
    onUserInteraction: OnUserInteraction,
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Каталог руйнувань",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
@Preview
private fun DestructionsContentPreview() {
    PreviewContainer {
        DestructionsContent(
            state = DestructionsState(email = "", password = ""),
            onUserInteraction = {},
        )
    }
}
