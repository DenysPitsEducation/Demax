package com.demax.feature.resources.composable

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
import com.demax.feature.resources.ResourcesViewModel
import com.demax.feature.resources.mapper.ResourcesUiMapper
import com.demax.feature.resources.model.ResourceUiModel
import com.demax.feature.resources.model.ResourcesUiModel
import com.demax.feature.resources.mvi.ResourcesIntent
import com.demax.feature.resources.mvi.ResourcesSideEffect
import com.demax.feature.resources.navigation.ResourcesRouter
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (ResourcesIntent) -> Unit

@Composable
fun ResourcesScreen(navController: NavHostController) {
    val viewModel: ResourcesViewModel = koinInject()
    val router: ResourcesRouter = koinInject()
    val mapper: ResourcesUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is ResourcesSideEffect.OpenResourceDetails -> router.openResourceDetails(navController, sideEffect.id)
            is ResourcesSideEffect.OpenResourceEdit -> router.openResourceEdit(navController)
            is ResourcesSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    ResourcesContent(model = uiModel, onUserInteraction = { viewModel.onIntent(it) })
}

@Composable
private fun ResourcesContent(
    model: ResourcesUiModel,
    onUserInteraction: OnUserInteraction,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Каталог запитів на ресурси",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = model.searchInput,
            onValueChange = {
                onUserInteraction(ResourcesIntent.SearchInputChanged(it))
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
                        onUserInteraction(ResourcesIntent.FilterClicked)
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
                        onUserInteraction(ResourcesIntent.AddResourceClicked)
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
            items(model.resourceUiModels) { destructionItemUiModel ->
                ResourceComposable(model = destructionItemUiModel, onUserInteraction)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun createResourceUiModelsMock() = listOf(
    ResourceUiModel(
        id = 4373,
        imageUrl = "https://picsum.photos/300/200",
        name = "Антисептичні серветки",
        category = "Медичні засоби",
        progress = ResourceUiModel.ProgressUiModel(
            progress = 0.3,
            amount = "3/10",
            text = "В процесі",
            color = Color(0xFF0043CE),
        ),
        status = ResourceUiModel.StatusUiModel(
            text = "Активне",
            background = Color(0xFFF2F487)
        )
    ),
    ResourceUiModel(
        id = 8246,
        imageUrl = "https://picsum.photos/300/200",
        name = "Цегла",
        category = "Будівельні матеріали",
        progress = ResourceUiModel.ProgressUiModel(
            progress = 1.0,
            amount = "10/10",
            text = "Завершено",
            color = Color(0xFF198038),
        ),
        status = ResourceUiModel.StatusUiModel(
            text = "Виконане",
            background = Color(0xFFC6EB88)
        )
    ),
)

@Composable
@Preview
private fun ResourcesContentPreview() {
    PreviewContainer {
        ResourcesContent(
            model = ResourcesUiModel(
                searchInput = "",
                showAddDestructionButton = true,
                resourceUiModels = createResourceUiModelsMock()
            ),
            onUserInteraction = {},
        )
    }
}
