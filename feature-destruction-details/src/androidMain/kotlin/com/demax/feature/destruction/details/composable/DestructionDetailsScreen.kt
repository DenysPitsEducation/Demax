package com.demax.feature.destruction.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.destruction.details.mvi.DestructionDetailsIntent
import com.demax.feature.destruction.details.navigation.DestructionDetailsRouter
import com.demax.feature.destruction.details.DestructionDetailsViewModel
import com.demax.feature.destruction.details.mapper.DestructionDetailsUiMapper
import com.demax.feature.destruction.details.model.DestructionDetailsUiModel
import com.demax.feature.destruction.details.model.DestructionStatisticsUiModel
import com.demax.feature.destruction.details.model.NeedUiModel
import com.demax.feature.destruction.details.model.ProgressUiModel
import com.demax.feature.destruction.details.model.ResourceNeedsBlockUiModel
import com.demax.feature.destruction.details.model.VolunteerNeedsBlockUiModel
import com.demax.feature.destruction.details.mvi.DestructionDetailsSideEffect
import com.demax.feature.destruction.details.navigation.DestructionDetailsPayload
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

internal typealias OnUserInteraction = (DestructionDetailsIntent) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestructionDetailsScreen(navController: NavHostController, payload: DestructionDetailsPayload) {
    val viewModel: DestructionDetailsViewModel = koinInject(parameters = { parametersOf(payload) })
    val router: DestructionDetailsRouter = koinInject()
    val mapper: DestructionDetailsUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToDestructionDetailsUiModel(state)
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current
    val onUserInteraction: OnUserInteraction = { viewModel.onIntent(it) }

    val volunteerBottomSheetModel = state.volunteerHelpBottomSheet?.let {
        mapper.mapToVolunteerBottomSheetUiModel(it)
    }
    val volunteerHelpBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showVolunteerHelpBottomSheet by remember { mutableStateOf(false) }
    val dismissVolunteerHelpBottomSheet: () -> Unit = {
        scope.launch {
            volunteerHelpBottomSheetState.hide()
            showVolunteerHelpBottomSheet = false
        }
    }
    val showVolunteerDatePickerDialog = remember { mutableStateOf(false) }

    val resourceBottomSheetModel = state.resourceHelpBottomSheet?.let {
        mapper.mapToResourceBottomSheetUiModel(it)
    }
    val resourceHelpBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showResourceHelpBottomSheet by remember { mutableStateOf(false) }
    val dismissResourceHelpBottomSheet: () -> Unit = {
        scope.launch {
            resourceHelpBottomSheetState.hide()
            showResourceHelpBottomSheet = false
        }
    }
    val showResourceDatePickerDialog = remember { mutableStateOf(false) }

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is DestructionDetailsSideEffect.OpenVolunteerHelpBottomSheet -> {
                showVolunteerHelpBottomSheet = true
                volunteerHelpBottomSheetState.show()
            }
            is DestructionDetailsSideEffect.OpenVolunteerHelpDatePicker -> {
                showVolunteerDatePickerDialog.value = true
            }
            is DestructionDetailsSideEffect.ShowSnackbar -> scope.launch {
                snackbarHostState.showSnackbar(sideEffect.text)
            }
            is DestructionDetailsSideEffect.OpenResourceHelpBottomSheet -> {
                showResourceHelpBottomSheet = true
                resourceHelpBottomSheetState.show()
            }
            is DestructionDetailsSideEffect.OpenResourceHelpDatePicker -> {
                showResourceDatePickerDialog.value = true
            }
        }
    }

    if (uiModel != null && volunteerBottomSheetModel != null) {
        DestructionDetailsContent(model = uiModel, onUserInteraction = onUserInteraction)
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    if (showVolunteerHelpBottomSheet && volunteerBottomSheetModel != null) {
        VolunteerHelpBottomSheet(
            dismissAction = dismissVolunteerHelpBottomSheet,
            bottomSheetState = volunteerHelpBottomSheetState,
            uiModel = volunteerBottomSheetModel,
            onUserInteraction = onUserInteraction,
            showDatePickerDialog = showVolunteerDatePickerDialog
        )
    }

    if (showResourceHelpBottomSheet && resourceBottomSheetModel != null) {
        ResourceHelpBottomSheet(
            dismissAction = dismissResourceHelpBottomSheet,
            bottomSheetState = resourceHelpBottomSheetState,
            uiModel = resourceBottomSheetModel,
            onUserInteraction = onUserInteraction,
            showDatePickerDialog = showResourceDatePickerDialog
        )
    }
}



@Composable
private fun DestructionDetailsContent(
    model: DestructionDetailsUiModel,
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
            text = "Руйнування",
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
            Text(text = "Тип будівлі", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.buildingType)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Адреса", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.address)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Масштаб руйнувань", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Кількість зруйнованих поверхів: " + model.destructionStatistics.destroyedFloors)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Кількість зруйнованих секцій (під’їздів): " + model.destructionStatistics.destroyedSections)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Дата руйнування", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.destructionDate)
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
        Spacer(modifier = Modifier.height(16.dp))
        VolunteerNeedsBlockComposable(model.volunteerNeedsBlock, onUserInteraction)
        Spacer(modifier = Modifier.height(16.dp))
        ResourceNeedsBlockComposable(model.resourceNeedsBlock, onUserInteraction)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun VolunteerNeedsBlockComposable(
    model: VolunteerNeedsBlockUiModel,
    onUserInteraction: OnUserInteraction
) {
    Column {
        Text(
            text = "Потреби у волонтерах (спеціалізації):",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        model.needs.forEach { needModel ->
            Spacer(modifier = Modifier.height(8.dp))
            NeedComposable(needModel)
        }
        if (model.showHelpButton) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onUserInteraction(DestructionDetailsIntent.ProvideVolunteerHelpClicked)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Надати волонтерську допомогу")
            }
        }
    }
}

@Composable
private fun ResourceNeedsBlockComposable(model: ResourceNeedsBlockUiModel, onUserInteraction: OnUserInteraction) {
    Column {
        Text(
            text = "Потреби у ресурсах (запити):",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        model.needs.forEach { needModel ->
            Spacer(modifier = Modifier.height(8.dp))
            NeedComposable(needModel)
        }
        if (model.showHelpButton) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onUserInteraction(DestructionDetailsIntent.ProvideResourceHelpClicked)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Надати допомогу у ресурсах")
            }
        }
        if (model.showAddResourcesButton) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    // TODO Pits:
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Створити запит на ресурс")
            }
        }
    }
}

@Composable
private fun NeedComposable(model: NeedUiModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = model.name,
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(
                    text = model.progress.amount,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = model.progress.text,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { model.progress.progress.toFloat() },
                modifier = Modifier.width(130.dp),
                strokeCap = StrokeCap.Round,
                color = model.progress.color,
                trackColor = Color(0xFFE0E0E0),
            )
        }
    }
}

fun createDestructionDetailsUiModelMock() = DestructionDetailsUiModel(
    imageUrl = "https://picsum.photos/1200/800",
    status = "Активне",
    buildingType = "Житловий будинок",
    address = "вул Чорновола, 28",
    destructionStatistics = DestructionStatisticsUiModel(
        destroyedFloors = "10",
        destroyedSections = "1"
    ),
    destructionDate = "08/07/2024",
    description = "Будівля зазнала невиправних руйнувань, приблизна кількість жертв становить ...",
    volunteerNeedsBlock = VolunteerNeedsBlockUiModel(
        needs = listOf(
            NeedUiModel(
                name = "Психотерапія",
                progress = ProgressUiModel(
                    progress = 0.3,
                    amount = "3/10",
                    text = "В процесі",
                    color = Color(0xFF0043CE),
                ),
            ),
            NeedUiModel(
                name = "Педіатрія",
                progress = ProgressUiModel(
                    progress = 0.3,
                    amount = "3/10",
                    text = "В процесі",
                    color = Color(0xFF0043CE),
                ),
            ),
        ),
        showHelpButton = true,
    ),
    resourceNeedsBlock = ResourceNeedsBlockUiModel(
        needs = listOf(
            NeedUiModel(
                name = "Зарядні пристрої",
                progress = ProgressUiModel(
                    progress = 1.0,
                    amount = "5/5",
                    text = "Завершено",
                    color = Color(0xFF198038),
                ),
            ),
        ),
        showHelpButton = true,
        showAddResourcesButton = true,
    ),
)

@Composable
@Preview
private fun DestructionDetailsContentPreview() {
    PreviewContainer {
        DestructionDetailsContent(
            model = createDestructionDetailsUiModelMock(),
            onUserInteraction = {},
        )
    }
}
