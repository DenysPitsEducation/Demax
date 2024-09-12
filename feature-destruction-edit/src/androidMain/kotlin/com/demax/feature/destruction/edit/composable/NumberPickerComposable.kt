package com.demax.feature.destruction.edit.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demax.core.ui.PreviewContainer
import com.demax.core.utils.formatFractionalPart
import com.demax.feature.destruction.edit.R

@Composable
fun NumberPickerComposable(
    quantity: Int,
    onQuantityChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            modifier = Modifier.size(36.dp),
            enabled = quantity > 0,
            onClick = {
                onQuantityChanged(quantity - 1)
            }
        ) {
            Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.width(4.dp))
        IconButton(
            modifier = Modifier.size(36.dp),
            onClick = {
                onQuantityChanged(quantity + 1)
            }
        ) {
            Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = null)
        }
    }
}

@Composable
@Preview
private fun NumberPickerComposablePreview() {
    PreviewContainer {
        NumberPickerComposable(quantity = 1, onQuantityChanged = {})
    }
}
