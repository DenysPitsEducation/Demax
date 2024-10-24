package com.demax.feature.destruction.edit.composable

import android.app.TimePickerDialog
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.destruction.edit.DestructionEditViewModel
import com.demax.feature.destruction.edit.mapper.DestructionEditUiMapper
import com.demax.feature.destruction.edit.model.DestructionEditUiModel
import com.demax.feature.destruction.edit.model.PredictionSwitchUiModel
import com.demax.feature.destruction.edit.mvi.DestructionEditIntent
import com.demax.feature.destruction.edit.mvi.DestructionEditSideEffect
import com.demax.feature.destruction.edit.navigation.DestructionEditPayload
import com.demax.feature.destruction.edit.navigation.DestructionEditRouter
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

internal typealias OnUserInteraction = (DestructionEditIntent) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestructionEditScreen(navController: NavHostController, payload: DestructionEditPayload) {
    val viewModel: DestructionEditViewModel = koinInject(parameters = { parametersOf(payload) })
    val router: DestructionEditRouter = koinInject()
    val mapper: DestructionEditUiMapper = koinInject()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current
    val onUserInteraction: OnUserInteraction = { viewModel.onIntent(it) }

    val resourcesBottomSheetModel = state.domainModel?.needsDomainModel?.let {
        mapper.mapToNeedsBottomSheetUiModel(it)
    }
    val resourcesBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showResourcesBottomSheet by remember { mutableStateOf(false) }
    val dismissResourcesBottomSheet: () -> Unit = {
        scope.launch {
            resourcesBottomSheetState.hide()
            showResourcesBottomSheet = false
        }
    }

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is DestructionEditSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
            is DestructionEditSideEffect.ShowResourcesEditBottomSheet -> {
                showResourcesBottomSheet = true
                resourcesBottomSheetState.show()
            }
        }
    }

    if (uiModel != null) {
        DestructionEditContent(
            model = uiModel,
            onUserInteraction = onUserInteraction,
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (showResourcesBottomSheet && resourcesBottomSheetModel != null) {
        ResourcesBottomSheet(
            dismissAction = dismissResourcesBottomSheet,
            bottomSheetState = resourcesBottomSheetState,
            uiModel = resourcesBottomSheetModel,
            onUserInteraction = onUserInteraction,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DestructionEditContent(
    model: DestructionEditUiModel,
    onUserInteraction: OnUserInteraction,
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                onUserInteraction(DestructionEditIntent.ImageSelected(File(uri)))
            }
        }

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
        Box(
            modifier = Modifier
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                .fillMaxWidth()
                .aspectRatio(328 * 1f / 226),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = model.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            if (model.image == null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.address,
            onValueChange = {
                onUserInteraction(DestructionEditIntent.AddressChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next,
            ),
            label = { Text(text = "Адреса") },
            modifier = Modifier.fillMaxWidth(),
        )
        var expandBuildingTypeDropdown by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(12.dp))
        ExposedDropdownMenuBox(
            expanded = expandBuildingTypeDropdown,
            onExpandedChange = { expandBuildingTypeDropdown = it },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = model.buildingType,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text("Тип будівлі") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandBuildingTypeDropdown) },
            )
            ExposedDropdownMenu(
                expanded = expandBuildingTypeDropdown,
                onDismissRequest = { expandBuildingTypeDropdown = false },
            ) {
                model.dropDownBuildingTypes.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.title) },
                        onClick = {
                            onUserInteraction(
                                DestructionEditIntent.BuildingTypeValueClicked(
                                    category.type
                                )
                            )
                            expandBuildingTypeDropdown = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        AnimatedVisibility(model.predictionSwitch != null) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Прогнозування пакетів допомоги")
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(checked = model.predictionSwitch!!.isChecked, onCheckedChange = {
                        onUserInteraction(DestructionEditIntent.PredictionSwitchClicked(it))
                    })
                }
            }
        }
        AnimatedVisibility(model.predictionSwitch?.isChecked == true) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = model.numberOfApartments,
                    onValueChange = {
                        onUserInteraction(DestructionEditIntent.NumberOfApartmentsChanged(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    label = { Text(text = "Кількість квартир") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = model.apartmentsSquare,
                    onValueChange = {
                        onUserInteraction(DestructionEditIntent.ApartmentsSquareChanged(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                    label = { Text(text = "Загальна площа квартир (м2)") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.destroyedFloors,
            onValueChange = {
                onUserInteraction(DestructionEditIntent.DestroyedFloorsChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = { Text(text = "Кількість зруйнованих поверхів") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.destroyedSections,
            onValueChange = {
                onUserInteraction(DestructionEditIntent.DestroyedSectionsChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = { Text(text = "Кількість зруйнованих секцій (під’їздів)") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.destroyedPercentage,
            onValueChange = {
                onUserInteraction(DestructionEditIntent.DestroyedPercentageChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = { Text(text = "Відсоток руйнування (%)") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = model.isArchitecturalMonument,
                onCheckedChange = {
                    onUserInteraction(DestructionEditIntent.ArchitectureMonumentCheckboxClicked)
                },
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Архітектурна пам’ятка")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = model.containsDangerousSubstances,
                onCheckedChange = {
                    onUserInteraction(DestructionEditIntent.DangerousSubstancesCheckboxClicked)
                },
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Містить небезпечні речовини")
        }

        val datePickerState = rememberDatePickerState()
        var showDatePickerDialog by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(12.dp))
        Box {
            OutlinedTextField(
                value = model.destructionDate,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Дата руйнування") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusProperties { canFocus = false }
            )
            Box(modifier = Modifier
                .matchParentSize()
                .clickable {
                    showDatePickerDialog = !showDatePickerDialog
                })
        }

        LaunchedEffect(datePickerState.selectedDateMillis) {
            onUserInteraction(DestructionEditIntent.DestructionDateSelected(datePickerState.selectedDateMillis))
        }

        if (showDatePickerDialog) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePickerDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = { showDatePickerDialog = false },
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePickerDialog = false },
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        val timePickerState = rememberTimePickerState(is24Hour = true)
        var showTimePickerDialog by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(12.dp))
        Box {
            OutlinedTextField(
                value = model.destructionTime,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Час руйнування") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select time"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusProperties { canFocus = false }
            )
            Box(modifier = Modifier
                .matchParentSize()
                .clickable {
                    showTimePickerDialog = !showTimePickerDialog
                })
        }

        LaunchedEffect(timePickerState.minute, timePickerState.hour) {
            onUserInteraction(
                DestructionEditIntent.DestructionTimeSelected(
                    timePickerState.hour,
                    timePickerState.minute
                )
            )
        }

        if (showTimePickerDialog) {
            BasicAlertDialog(
                onDismissRequest = {
                    showTimePickerDialog = false
                },
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            ) {
                Surface {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TimePicker(state = timePickerState)
                        Button(
                            onClick = {
                                showTimePickerDialog = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Зберегти зміни"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.description,
            onValueChange = {
                onUserInteraction(DestructionEditIntent.DescriptionChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done,
            ),
            label = { Text(text = "Опис") },
            modifier = Modifier.fillMaxWidth(),
        )


        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onUserInteraction(DestructionEditIntent.EditResourcesClicked)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Уточнити необхідні ресурси"
            )
        }
        Button(
            onClick = {
                onUserInteraction(DestructionEditIntent.CreateButtonClicked)
            },
            enabled = model.createButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Зберегти зміни"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

fun createDestructionEditUiModelMock() = DestructionEditUiModel(
    image = null,
    buildingType = "Житловий будинок",
    dropDownBuildingTypes = listOf(),
    address = "м. Київ, вул. Володимирська 4",
    predictionSwitch = PredictionSwitchUiModel(isChecked = true),
    numberOfApartments = "2",
    apartmentsSquare = "50",
    destroyedFloors = "2",
    destroyedSections = "1",
    destroyedPercentage = "23",
    isArchitecturalMonument = true,
    containsDangerousSubstances = false,
    destructionDate = "2024/12/12",
    destructionTime = "20:45",
    description = "Description",
    createButtonEnabled = true,
)

@Composable
@Preview(device = "spec:width=1080px,height=5340px,dpi=440")
private fun DestructionDetailsContentPreview() {
    PreviewContainer {
        DestructionEditContent(
            model = createDestructionEditUiModelMock(),
            onUserInteraction = {},
        )
    }
}
