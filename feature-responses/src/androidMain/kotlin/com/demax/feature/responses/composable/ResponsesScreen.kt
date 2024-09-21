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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.demax.feature.responses.mvi.ResponsesSideEffect
import com.demax.feature.responses.navigation.ResponsesRouter
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (ResponsesIntent) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsesScreen(navController: NavHostController) {
    val viewModel: ResponsesViewModel = koinInject()
    val router: ResponsesRouter = koinInject()
    val mapper: ResponsesUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val scope = rememberCoroutineScope()
    val onUserInteraction: OnUserInteraction = { viewModel.onIntent(it) }
    val snackbarHostState = LocalSnackbarHostState.current
    val filterBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterBottomSheet by remember { mutableStateOf(false) }

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is ResponsesSideEffect.OpenFilterBottomSheet -> {
                showFilterBottomSheet = true
                filterBottomSheetState.show()
            }
            is ResponsesSideEffect.OpenProfile -> router.openProfile(navController, sideEffect.id)
            is ResponsesSideEffect.OpenDestructionDetails -> router.openDestructionDetails(navController, sideEffect.id)
            is ResponsesSideEffect.OpenResourceDetails -> router.openResourceDetails(navController, sideEffect.id)
            is ResponsesSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    ResponsesContent(model = uiModel, onUserInteraction = onUserInteraction)

    if (showFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showFilterBottomSheet = false
            },
            sheetState = filterBottomSheetState,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Фільтрувати",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                uiModel.filterUiModels.forEachIndexed { index, filter ->
                    if (index != 0) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Text(
                        text = filter.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    filter.options.forEach { filterOption ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onUserInteraction(
                                        ResponsesIntent.FilterOptionClicked(
                                            filterOption.type
                                        )
                                    )
                                }
                        ) {
                            Checkbox(
                                checked = filterOption.isSelected,
                                modifier = Modifier.size(40.dp),
                                onCheckedChange = {
                                    onUserInteraction(ResponsesIntent.FilterOptionClicked(filterOption.type))
                                },
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(filterOption.title)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            filterBottomSheetState.hide()
                            showFilterBottomSheet = false
                        }
                    }
                ) {
                    Text("Обрати фільтр")
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
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
        id = "0",
        profile = ProfileUiModel(
            id = "0",
            name = "Ксюша",
            imageUrl = null,
        ),
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
        showConfirmationButtons = true,
    ),
    ResponseUiModel(
        id = "1",
        profile = ProfileUiModel(
            id = "1",
            name = "Маша",
            imageUrl = null,
        ),
        type = ResponseTypeUiModel.Resource(
            resources = listOf(
                ResourceUiModel(
                    id = "0",
                    imageUrl = null,
                    category = "Медичні засоби",
                    name = "Антисептичні серветки",
                    quantity = 5,
                )
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
                filterUiModels = listOf(),
                responseUiModels = createResponseUiModelsMock()
            ),
            onUserInteraction = {},
        )
    }
}
