package com.demax.feature.destructions.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
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
import com.demax.feature.destructions.DestructionsViewModel
import com.demax.feature.destructions.mapper.DestructionsUiMapper
import com.demax.feature.destructions.model.DestructionItemUiModel
import com.demax.feature.destructions.model.DestructionsUiModel
import com.demax.feature.destructions.mvi.DestructionsIntent
import com.demax.feature.destructions.mvi.DestructionsSideEffect
import com.demax.feature.destructions.navigation.DestructionsRouter
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (DestructionsIntent) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestructionsScreen(navController: NavHostController) {
    val viewModel: DestructionsViewModel = koinInject()
    val router: DestructionsRouter = koinInject()
    val mapper: DestructionsUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current
    val onUserInteraction: OnUserInteraction = { viewModel.onIntent(it) }
    val scope = rememberCoroutineScope()
    val sortingBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSortingBottomSheet by remember { mutableStateOf(false) }
    val filterBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterBottomSheet by remember { mutableStateOf(false) }

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is DestructionsSideEffect.OpenDestructionDetails -> router.openDestructionDetails(
                navController,
                sideEffect.id
            )

            is DestructionsSideEffect.OpenSortBottomSheet -> {
                showSortingBottomSheet = true
                sortingBottomSheetState.show()
            }

            is DestructionsSideEffect.OpenFilterBottomSheet -> {
                showFilterBottomSheet = true
                filterBottomSheetState.show()
            }
        }
    }

    DestructionsContent(model = uiModel, onUserInteraction = onUserInteraction)

    if (showSortingBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSortingBottomSheet = false
            },
            sheetState = sortingBottomSheetState,
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Сортувати",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                uiModel.sortUiModels.forEach { sortUiModel ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onUserInteraction(DestructionsIntent.SortItemClicked(sortUiModel.type))
                            }
                    ) {
                        RadioButton(
                            selected = sortUiModel.isSelected,
                            onClick = {
                                onUserInteraction(DestructionsIntent.SortItemClicked(sortUiModel.type))
                            },
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(sortUiModel.title)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            sortingBottomSheetState.hide()
                            showSortingBottomSheet = false
                        }
                    }
                ) {
                    Text("Обрати сортування")
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

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
                                    onUserInteraction(DestructionsIntent.FilterOptionClicked(filterOption.type))
                                }
                        ) {
                            Checkbox(
                                checked = filterOption.isSelected,
                                modifier = Modifier.size(40.dp),
                                onCheckedChange = {
                                    onUserInteraction(DestructionsIntent.FilterOptionClicked(filterOption.type))
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
private fun DestructionsContent(
    model: DestructionsUiModel,
    onUserInteraction: OnUserInteraction,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Каталог руйнувань",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onUserInteraction(DestructionsIntent.SortClicked)
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
                        onUserInteraction(DestructionsIntent.FilterClicked)
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                Text("Фільтрувати", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.FilterAlt,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onUserInteraction(DestructionsIntent.AddDestructionClicked)
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
                Icon(
                    Icons.Default.Add,
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
            items(model.destructionItemUiModels) { destructionItemUiModel ->
                DestructionItemComposable(
                    model = destructionItemUiModel,
                    onUserInteraction = onUserInteraction,
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun createDestructionItemUiModelsMock() = listOf(
    DestructionItemUiModel(
        id = "4373",
        imageUrl = "https://picsum.photos/300/200",
        buildingType = "житловий будинок",
        address = "вул Чорновола, 28",
        destructionDate = "08/07/2024",
        resourceProgress = DestructionItemUiModel.ProgressUiModel(
            progress = 0.3,
            percentage = "30%",
            text = "В процесі",
            color = Color(0xFF0043CE),
        ),
        volunteerProgress = DestructionItemUiModel.ProgressUiModel(
            progress = 1.0,
            percentage = "100%",
            text = "Завершено",
            color = Color(0xFF198038),
        ),
        specializations = listOf("Психотерапія", "Педіатрія"),
        status = DestructionItemUiModel.StatusUiModel(
            text = "Активне",
            background = Color(0xFFF2F487)
        )
    ),
    DestructionItemUiModel(
        id = "8246",
        imageUrl = "https://picsum.photos/300/200",
        buildingType = "медичний заклад",
        address = "вул Лобановського, 28",
        destructionDate = "08/07/2022",
        resourceProgress = DestructionItemUiModel.ProgressUiModel(
            progress = 1.0,
            percentage = "100%",
            text = "Завершено",
            color = Color(0xFF198038),
        ),
        volunteerProgress = DestructionItemUiModel.ProgressUiModel(
            progress = 0.3,
            percentage = "30%",
            text = "В процесі",
            color = Color(0xFF0043CE),
        ),
        specializations = listOf("Психотерапія", "Педіатрія"),
        status = DestructionItemUiModel.StatusUiModel(
            text = "Виконане",
            background = Color(0xFFC6EB88)
        )
    ),
)

@Composable
@Preview
private fun DestructionsContentPreview() {
    PreviewContainer {
        DestructionsContent(
            model = DestructionsUiModel(
                sortUiModels = emptyList(),
                filterUiModels = emptyList(),
                showAddDestructionButton = true,
                destructionItemUiModels = createDestructionItemUiModelsMock()
            ),
            onUserInteraction = {},
        )
    }
}
