package com.demax.feature.responses.composable

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
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.responses.ResponsesViewModel
import com.demax.feature.responses.mapper.ResponsesUiMapper
import com.demax.feature.responses.model.DestructionUiModel
import com.demax.feature.responses.model.ProfileUiModel
import com.demax.feature.responses.model.ResourceUiModel
import com.demax.feature.responses.model.ResponseTypeUiModel
import com.demax.feature.responses.model.ResponseUiModel
import com.demax.feature.responses.model.ResponsesUiModel
import com.demax.feature.responses.model.StatusUiModel
import com.demax.feature.responses.mvi.ResponsesIntent
import com.demax.feature.responses.navigation.ResponsesRouter
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (ResponsesIntent) -> Unit

@Composable
fun ResponsesScreen(navController: NavHostController) {
    val viewModel: ResponsesViewModel = koinInject()
    val router: ResponsesRouter = koinInject()
    val mapper: ResponsesUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        /*when (sideEffect) {
            is ResourcesSideEffect.OpenMainScreen -> router.openMainScreen(navController)
            is ResourcesSideEffect.OpenPasswordResetScreen -> router.openPasswordResetScreen(navController)
            is ResourcesSideEffect.OpenRegistrationScreen -> router.openRegistrationScreen(navController)
            is ResourcesSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }*/
    }

    ResponsesContent(model = uiModel, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
private fun ResponsesContent(
    model: ResponsesUiModel,
    onUserInteraction: OnUserInteraction,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Каталог відгуків",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onUserInteraction(ResponsesIntent.SortClicked)
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                Text("Сортувати", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.SwapVert,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onUserInteraction(ResponsesIntent.FilterClicked)
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                Text("Тип відгуку", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.FilterAlt,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
            items(model.responseUiModels) { destructionItemUiModel ->
                ResourceComposable(model = destructionItemUiModel, onUserInteraction)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun createResponseUiModelsMock() = listOf(
    ResponseUiModel(
        id = 0,
        profile = ProfileUiModel(
            id = 0,
            name = "Ксюша",
            imageUrl = null,
        ),
        type = ResponseTypeUiModel.Volunteer(
            destruction = DestructionUiModel(
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
        showConfirmationButtons = true,
    ),
    ResponseUiModel(
        id = 1,
        profile = ProfileUiModel(
            id = 1,
            name = "Маша",
            imageUrl = null,
        ),
        type = ResponseTypeUiModel.Resource(
            resource = ResourceUiModel(
                id = 0,
                imageUrl = null,
                category = "Медичні засоби",
                name = "Антисептичні серветки",
                quantity = 5,
            ),
        ),
        status = StatusUiModel(
            text = "Схвалено",
            background = Color(0xFFC6EB88)
        ),
        showConfirmationButtons = true,
    ),
)

@Composable
@Preview
private fun ResponsesContentPreview() {
    PreviewContainer {
        ResponsesContent(
            model = ResponsesUiModel(
                responseUiModels = createResponseUiModelsMock()
            ),
            onUserInteraction = {},
        )
    }
}
