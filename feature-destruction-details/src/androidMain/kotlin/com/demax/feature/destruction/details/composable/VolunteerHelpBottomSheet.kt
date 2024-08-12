package com.demax.feature.destruction.details.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demax.core.ui.PreviewContainer
import com.demax.feature.destruction.details.model.VolunteerNeedBottomSheetUiModel
import com.demax.feature.destruction.details.model.VolunteerHelpBottomSheetUiModel
import com.demax.feature.destruction.details.mvi.VolunteerHelpBottomSheetIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VolunteerHelpBottomSheet(
    dismissAction: () -> Unit,
    bottomSheetState: SheetState,
    uiModel: VolunteerHelpBottomSheetUiModel,
    onUserInteraction: OnUserInteraction,
    showDatePickerDialog: MutableState<Boolean>,
) {
    val datePickerState = rememberDatePickerState()

    ModalBottomSheet(
        onDismissRequest = dismissAction,
        sheetState = bottomSheetState,
    ) {
        VolunteerHelpBottomSheetContent(
            dismissAction = dismissAction,
            uiModel = uiModel,
            onUserInteraction = onUserInteraction
        )
    }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        onUserInteraction(VolunteerHelpBottomSheetIntent.DateChanged(datePickerState.selectedDateMillis))
    }

    if (showDatePickerDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePickerDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = { showDatePickerDialog.value = false },
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePickerDialog.value = false },
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
internal fun VolunteerHelpBottomSheetContent(
    dismissAction: () -> Unit,
    uiModel: VolunteerHelpBottomSheetUiModel,
    onUserInteraction: OnUserInteraction,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Надати волонтерську допомогу",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Коли можете приїхати?",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            OutlinedTextField(
                value = uiModel.dateInputText.orEmpty(),
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Оберіть дату") },
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
                    onUserInteraction(VolunteerHelpBottomSheetIntent.DateInputClicked)
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Який тип допомоги можете закрити?",
            style = MaterialTheme.typography.titleMedium
        )
        uiModel.needs.forEach { need ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onUserInteraction(
                            VolunteerHelpBottomSheetIntent.SpecializationOptionClicked(need.title)
                        )
                    }
            ) {
                Checkbox(
                    checked = need.isSelected,
                    modifier = Modifier.size(40.dp),
                    onCheckedChange = {
                        onUserInteraction(
                            VolunteerHelpBottomSheetIntent.SpecializationOptionClicked(need.title)
                        )
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(need.title)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = uiModel.isButtonEnabled,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                dismissAction()
                onUserInteraction(VolunteerHelpBottomSheetIntent.ProvideHelpButtonClicked)
            }
        ) {
            Text("Надати допомогу")
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
@Preview
private fun VolunteerHelpBottomSheetContentPreview() {
    PreviewContainer {
        VolunteerHelpBottomSheetContent(
            dismissAction = {},
            uiModel = VolunteerHelpBottomSheetUiModel(
                dateInputText = null,
                needs = listOf(
                    VolunteerNeedBottomSheetUiModel(title = "Психотерапевт", isSelected = true),
                    VolunteerNeedBottomSheetUiModel(title = "Водій", isSelected = false),
                ),
                isButtonEnabled = true,
            ),
            onUserInteraction = {}
        )
    }
}