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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.demax.feature.destructions.mvi.DestructionsIntent
import com.demax.feature.destructions.navigation.DestructionsRouter
import com.demax.feature.destructions.DestructionsViewModel
import com.demax.feature.destructions.mapper.DestructionsUiMapper
import com.demax.feature.destructions.model.DestructionItemUiModel
import com.demax.feature.destructions.model.DestructionsUiModel
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (DestructionsIntent) -> Unit

@Composable
fun DestructionsScreen(navController: NavHostController) {
    val viewModel: DestructionsViewModel = koinInject()
    val router: DestructionsRouter = koinInject()
    val mapper: DestructionsUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
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

    DestructionsContent(model = uiModel, onUserInteraction = { viewModel.onIntent(it) })
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
        OutlinedTextField(
            value = model.searchInput,
            onValueChange = {
                onUserInteraction(DestructionsIntent.SearchInputChanged(it))
            },
            trailingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null)
            },
            placeholder = {
                Text("Пошук за ключовими словами")
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
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
                DestructionItemComposable(model = destructionItemUiModel, onUserInteraction)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun createDestructionItemUiModelsMock() = listOf(
    DestructionItemUiModel(
        id = 4373,
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
        id = 8246,
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
                searchInput = "",
                showAddDestructionButton = true,
                destructionItemUiModels = createDestructionItemUiModelsMock()
            ),
            onUserInteraction = {},
        )
    }
}