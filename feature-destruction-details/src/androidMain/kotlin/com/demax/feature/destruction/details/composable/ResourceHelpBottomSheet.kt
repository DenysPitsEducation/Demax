package com.demax.feature.destruction.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demax.core.ui.PreviewContainer
import com.demax.feature.destruction.details.model.ResourceHelpBottomSheetUiModel
import com.demax.feature.destruction.details.model.ResourceNeedBottomSheetUiModel
import com.demax.feature.destruction.details.model.VolunteerNeedBottomSheetUiModel
import com.demax.feature.destruction.details.model.VolunteerHelpBottomSheetUiModel
import com.demax.feature.destruction.details.mvi.ResourceHelpBottomSheetIntent
import com.demax.feature.destruction.details.mvi.VolunteerHelpBottomSheetIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResourceHelpBottomSheet(
    dismissAction: () -> Unit,
    bottomSheetState: SheetState,
    uiModel: ResourceHelpBottomSheetUiModel,
    onUserInteraction: OnUserInteraction,
    showDatePickerDialog: MutableState<Boolean>,
) {
    val datePickerState = rememberDatePickerState()

    ModalBottomSheet(
        onDismissRequest = dismissAction,
        sheetState = bottomSheetState,
    ) {
        ResourceHelpBottomSheetContent(
            dismissAction = dismissAction,
            uiModel = uiModel,
            onUserInteraction = onUserInteraction
        )
    }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        onUserInteraction(ResourceHelpBottomSheetIntent.DateChanged(datePickerState.selectedDateMillis))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResourceHelpBottomSheetContent(
    dismissAction: () -> Unit,
    uiModel: ResourceHelpBottomSheetUiModel,
    onUserInteraction: OnUserInteraction,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Надати матеріальну допомогу",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Коли можете привезти?",
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
                    onUserInteraction(ResourceHelpBottomSheetIntent.DateInputClicked)
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Який тип потреби можете надати?",
            style = MaterialTheme.typography.titleMedium
        )
        uiModel.needs.forEach { need ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onUserInteraction(
                            ResourceHelpBottomSheetIntent.ResourceOptionClicked(need.title)
                        )
                    }
            ) {
                Checkbox(
                    checked = need.isSelected,
                    modifier = Modifier.size(40.dp),
                    onCheckedChange = {
                        onUserInteraction(
                            ResourceHelpBottomSheetIntent.ResourceOptionClicked(need.title)
                        )
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
                BasicTextField(
                    value = need.quantity.orEmpty(),
                    decorationBox = { textField ->
                        Box(
                            modifier = Modifier
                                .defaultMinSize(32.dp, 32.dp)
                                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (need.quantity == null) {
                                Text(text = "0", color = Color(0xFFA8A8A9))
                            } else {
                                textField()
                            }
                        }
                    },
                    onValueChange = {
                        onUserInteraction(
                            ResourceHelpBottomSheetIntent.ResourceQuantityChanged(
                                id = need.id,
                                quantity = it
                            )
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.width(IntrinsicSize.Min)
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
                onUserInteraction(ResourceHelpBottomSheetIntent.ProvideHelpButtonClicked)
            }
        ) {
            Text("Надати допомогу")
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
@Preview
private fun ResourceHelpBottomSheetContentPreview() {
    PreviewContainer {
        ResourceHelpBottomSheetContent(
            dismissAction = {},
            uiModel = ResourceHelpBottomSheetUiModel(
                dateInputText = null,
                needs = listOf(
                    ResourceNeedBottomSheetUiModel(
                        id = "0",
                        title = "Аптечка",
                        quantity = null,
                        isSelected = true
                    ),
                    ResourceNeedBottomSheetUiModel(
                        id = "1",
                        title = "Машина",
                        quantity = "1",
                        isSelected = false
                    ),
                ),
                isButtonEnabled = true,
            ),
            onUserInteraction = {}
        )
    }
}