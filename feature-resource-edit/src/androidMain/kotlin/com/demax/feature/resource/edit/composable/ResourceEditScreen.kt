package com.demax.feature.resource.edit.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
import com.demax.feature.resource.edit.navigation.ResourceEditPayload
import com.demax.feature.resource.edit.navigation.ResourceEditRouter
import dev.gitlive.firebase.storage.File
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

internal typealias OnUserInteraction = (ResourceEditIntent) -> Unit

@Composable
fun ResourceEditScreen(navController: NavHostController, payload: ResourceEditPayload) {
    val viewModel: ResourceEditViewModel = koinInject(parameters = { parametersOf(payload) })
    val router: ResourceEditRouter = koinInject()
    val mapper: ResourceEditUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        when (sideEffect) {
            is ResourceEditSideEffect.OpenPhotoPicker -> TODO()
            is ResourceEditSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }
    }

    if (uiModel != null) {
        ResourceDetailsContent(
            model = uiModel,
            onUserInteraction = { viewModel.onIntent(it) },
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResourceDetailsContent(
    model: ResourceEditUiModel,
    onUserInteraction: OnUserInteraction,
) {
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onUserInteraction(ResourceEditIntent.ImageSelected(File(uri)))
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
            text = "Запит на ресурс",
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
            value = model.name,
            onValueChange = {
                onUserInteraction(ResourceEditIntent.NameChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            label = { Text(text = "Назва ресурсу") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        var expandCategoryDropdown by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandCategoryDropdown,
            onExpandedChange = { expandCategoryDropdown = it },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = model.category,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text("Категорія") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandCategoryDropdown) },
            )
            ExposedDropdownMenu(
                expanded = expandCategoryDropdown,
                onDismissRequest = { expandCategoryDropdown = false },
            ) {
                model.dropDownCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.title) },
                        onClick = {
                            onUserInteraction(ResourceEditIntent.CategoryValueClicked(category.type))
                            expandCategoryDropdown = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.totalAmount,
            onValueChange = {
                onUserInteraction(ResourceEditIntent.TotalAmountChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = { Text(text = "Необхідна кількість") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.currentAmount,
            onValueChange = {
                onUserInteraction(ResourceEditIntent.CurrentAmountChanged(it))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = { Text(text = "Поточна кількість") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.description,
            onValueChange = {
                onUserInteraction(ResourceEditIntent.DescriptionChanged(it))
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
        var expandDestructionDropdown by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expandDestructionDropdown,
            onExpandedChange = { expandDestructionDropdown = it },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = model.destruction,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text("Руйнування") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandDestructionDropdown) },
            )
            ExposedDropdownMenu(
                expanded = expandDestructionDropdown,
                onDismissRequest = { expandDestructionDropdown = false },
            ) {
                model.dropDownDestructions.forEach { destruction ->
                    DropdownMenuItem(
                        text = { Text(destruction.title) },
                        onClick = {
                            onUserInteraction(ResourceEditIntent.DestructionValueClicked(destruction.id))
                            expandDestructionDropdown = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onUserInteraction(ResourceEditIntent.SaveButtonClicked)
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
    image = "https://picsum.photos/1200/800",
    name = "Антисептичні серветки",
    category = "Медичні засоби",
    dropDownCategories = emptyList(),
    totalAmount = "10",
    currentAmount = "8",
    description = "Чудові антисептичні серветки",
    destruction = "вул Чорновола, 28",
    dropDownDestructions = emptyList(),
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
