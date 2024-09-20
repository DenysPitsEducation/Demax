package com.demax.feature.help.history.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.navigation.ResourceHelpPayload
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.help.history.HelpHistoryViewModel
import com.demax.feature.help.history.mapper.HelpHistoryUiMapper
import com.demax.feature.help.history.model.DestructionUiModel
import com.demax.feature.help.history.model.ProfileUiModel
import com.demax.feature.help.history.model.ResourceUiModel
import com.demax.feature.help.history.model.ResponseTypeUiModel
import com.demax.feature.help.history.model.HelpUiModel
import com.demax.feature.help.history.model.HelpHistoryUiModel
import com.demax.feature.help.history.model.StatusUiModel
import com.demax.feature.help.history.mvi.HelpHistoryIntent
import com.demax.feature.help.history.mvi.HelpHistorySideEffect
import com.demax.feature.help.history.navigation.HelpHistoryPayload
import com.demax.feature.help.history.navigation.HelpHistoryRouter
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

internal typealias OnUserInteraction = (HelpHistoryIntent) -> Unit

@Composable
fun HelpHistoryScreen(navController: NavHostController, payload: HelpHistoryPayload) {
    val viewModel: HelpHistoryViewModel = koinInject { parametersOf(payload) }
    val router: HelpHistoryRouter = koinInject()
    val mapper: HelpHistoryUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is HelpHistorySideEffect.OpenProfile -> router.openProfile(navController, sideEffect.id)
            is HelpHistorySideEffect.OpenDestructionDetails -> router.openDestructionDetails(navController, sideEffect.id)
            is HelpHistorySideEffect.OpenResourceDetails -> router.openResourceDetails(navController, sideEffect.id)
            is HelpHistorySideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    HelpHistoryContent(model = uiModel, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
private fun HelpHistoryContent(
    model: HelpHistoryUiModel,
    onUserInteraction: OnUserInteraction,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Історія допомог",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        items(model.helpUiModels) { destructionItemUiModel ->
            HelpComposable(model = destructionItemUiModel, onUserInteraction)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Історія відгуків",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(20.dp))
            FeedbackComposable()
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

fun createHelpUiModelsMock() = listOf(
    HelpUiModel(
        id = "0",
        type = ResponseTypeUiModel.Volunteer(
            destruction = DestructionUiModel(
                id = "0",
                imageUrl = null,
                destructionDate = "13/06/2024",
                address = "вул Чорновола, 28"
            ),
            specializations = listOf("Водій", "Хороша людина", "Психолог")
        ),
        status = StatusUiModel(
            text = "Очікує розгляду",
            background = Color(0xFFF2F487)
        ),
    ),
    HelpUiModel(
        id = "1",
        type = ResponseTypeUiModel.Resource(
            resources = listOf(
                ResourceUiModel(
                    id = "0",
                    imageUrl = null,
                    category = "Медичні засоби",
                    name = "Антисептичні серветки",
                    quantity = 5,
                ),
            ),
        ),
        status = StatusUiModel(
            text = "Схвалено",
            background = Color(0xFFC6EB88)
        ),
    ),
)

@Composable
@Preview
private fun HelpHistoryContentPreview() {
    PreviewContainer {
        HelpHistoryContent(
            model = HelpHistoryUiModel(
                helpUiModels = createHelpUiModelsMock()
            ),
            onUserInteraction = {},
        )
    }
}
