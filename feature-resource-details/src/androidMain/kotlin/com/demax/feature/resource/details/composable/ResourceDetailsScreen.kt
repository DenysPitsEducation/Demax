package com.demax.feature.resource.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.resource.details.ResourceDetailsViewModel
import com.demax.feature.resource.details.mapper.ResourceDetailsUiMapper
import com.demax.feature.resource.details.model.AmountUiModel
import com.demax.feature.resource.details.model.DestructionUiModel
import com.demax.feature.resource.details.model.ResourceDetailsUiModel
import com.demax.feature.resource.details.mvi.ResourceDetailsIntent
import com.demax.feature.resource.details.mvi.ResourceDetailsSideEffect
import com.demax.feature.resource.details.navigation.ResourceDetailsRouter
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (ResourceDetailsIntent) -> Unit

@Composable
fun ResourceDetailsScreen(navController: NavHostController) {
    val viewModel: ResourceDetailsViewModel = koinInject()
    val router: ResourceDetailsRouter = koinInject()
    val mapper: ResourceDetailsUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is ResourceDetailsSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
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
    model: ResourceDetailsUiModel,
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
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Статус", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.status)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Назва ресурсу", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.name)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Категорія", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.category)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Потреби", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Необхідна кількість одиниць: " + model.amount.totalAmount)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Залишилося зібрати: " + model.amount.neededAmount)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Опис", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.description)
        }
        Spacer(modifier = Modifier.height(8.dp))
        DestructionComposable(model.destruction)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier.fillMaxWidth(),
            ) {
            Text(
                text = "Надати допомогу"
            )
        }
        if (model.showEditButton) {
            Button(
                onClick = {
                    /*TODO*/
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Модифікувати"
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun DestructionComposable(model: DestructionUiModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        AsyncImage(
            model = model.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .border(1.dp, Color(0xFFA8A8A9), CircleShape)
                .clip(CircleShape)
                .size(50.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Дата руйнування: ")
                    }
                    append(model.destructionDate)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Адреса: ")
                    }
                    append(model.address)
                }
            )
        }
    }
}

fun createDestructionDetailsUiModelMock() = ResourceDetailsUiModel(
    imageUrl = "https://picsum.photos/1200/800",
    status = "Активне",
    name = "Антисептичні серветки",
    category = "Медичні засоби",
    amount = AmountUiModel(
        totalAmount = 10,
        neededAmount = 8,
    ),
    description = "Чудові антисептичні серветки",
    destruction = DestructionUiModel(
        imageUrl = "https://picsum.photos/100/100",
        destructionDate = "13/06/2024",
        address = "вул Чорновола, 28",
    ),
    showEditButton = true,
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
