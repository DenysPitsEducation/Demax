package com.demax.feature.resource.edit.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.resource.edit.ResourceEditViewModel
import com.demax.feature.resource.edit.mapper.ResourceEditUiMapper
import com.demax.feature.resource.edit.model.ResourceEditUiModel
import com.demax.feature.resource.edit.mvi.ResourceEditIntent
import com.demax.feature.resource.edit.mvi.ResourceEditSideEffect
import com.demax.feature.resource.edit.navigation.ResourceEditRouter
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (ResourceEditIntent) -> Unit

@Composable
fun ResourceEditScreen(navController: NavHostController) {
    val viewModel: ResourceEditViewModel = koinInject()
    val router: ResourceEditRouter = koinInject()
    val mapper: ResourceEditUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is ResourceEditSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    if (uiModel != null) {
        ResourceDetailsContent(model = uiModel, onUserInteraction = { viewModel.onIntent(it) })
    } else {
        CircularProgressIndicator()
    }
}

@Composable
private fun ResourceDetailsContent(
    model: ResourceEditUiModel,
    onUserInteraction: OnUserInteraction,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Запит на ресурс",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = model.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .aspectRatio(328 * 1f / 226),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.status,
            onValueChange = {

            },
            label = { Text(text = "Статус") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.name,
            onValueChange = {

            },
            label = { Text(text = "Назва ресурсу") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.category,
            onValueChange = {

            },
            label = { Text(text = "Категорія") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.amount.toString(),
            onValueChange = {

            },
            label = { Text(text = "Кількість одиниць") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.description,
            onValueChange = {

            },
            label = { Text(text = "Опис") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.destruction,
            onValueChange = {

            },
            label = { Text(text = "Руйнування") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Зберегти зміни"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun createDestructionDetailsUiModelMock() = ResourceEditUiModel(
    imageUrl = "https://picsum.photos/1200/800",
    status = "Активне",
    name = "Антисептичні серветки",
    category = "Медичні засоби",
    amount = 10,
    description = "Чудові антисептичні серветки",
    destruction = "вул Чорновола, 28",
)

@Composable
@Preview
private fun DestructionDetailsContentPreview() {
    PreviewContainer {
        ResourceDetailsContent(
            model = createDestructionDetailsUiModelMock(),
            onUserInteraction = {},
        )
    }
}
