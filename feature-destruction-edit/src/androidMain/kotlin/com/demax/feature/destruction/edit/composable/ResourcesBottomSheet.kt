package com.demax.feature.destruction.edit.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demax.core.ui.PreviewContainer
import com.demax.feature.destruction.edit.model.NeedsUiModel
import com.demax.feature.destruction.edit.mvi.DestructionEditIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResourcesBottomSheet(
    dismissAction: () -> Unit,
    bottomSheetState: SheetState,
    uiModel: NeedsUiModel,
    onUserInteraction: OnUserInteraction,
) {
    ModalBottomSheet(
        onDismissRequest = dismissAction,
        sheetState = bottomSheetState,
    ) {
        ResourcesBottomSheetContent(
            dismissAction = dismissAction,
            uiModel = uiModel,
            onUserInteraction = onUserInteraction
        )
    }
}

@Composable
internal fun ResourcesBottomSheetContent(
    uiModel: NeedsUiModel,
    onUserInteraction: OnUserInteraction,
    dismissAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Потреби в ресурсах",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Кількість пакетів допомоги",
            style = MaterialTheme.typography.bodyLarge
        )
        NumberPickerComposable(
            quantity = uiModel.helpPackages,
            onQuantityChanged = {
                onUserInteraction(DestructionEditIntent.HelpPackagesQuantityChanged(it))
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        uiModel.specializations.forEach { specialization ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = specialization.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                NumberPickerComposable(
                    quantity = specialization.quantity,
                    onQuantityChanged = {
                        onUserInteraction(
                            DestructionEditIntent.SpecializationQuantityChanged(
                                quantity = it,
                                specializationName = specialization.name
                            )
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                dismissAction()
                onUserInteraction(DestructionEditIntent.SaveResourcesButtonClicked)
            }
        ) {
            Text("Зберегти")
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
@Preview
private fun VolunteerHelpBottomSheetContentPreview() {
    PreviewContainer {
        ResourcesBottomSheetContent(
            dismissAction = {},
            uiModel = NeedsUiModel(
                1,
                listOf(
                    NeedsUiModel.SpecializationUiModel("Медичний персонал", 5),
                    NeedsUiModel.SpecializationUiModel("Рятувальники", 0),
                    NeedsUiModel.SpecializationUiModel("Будівельні спеціалісти", 2),
                )
            ),
            onUserInteraction = {}
        )
    }
}