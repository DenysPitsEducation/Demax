package com.demax.feature.help.history.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demax.core.ui.PreviewContainer

@Composable
fun FeedbackComposable(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
    ) {
        Row {
            Text(
                text = "Анастасія Мороз:",
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "20.09.2024",
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Проявлено високий рівень відповідальності, пунктуальності, та самовіддачі",
        )
    }
}

@Composable
@Preview
private fun FeedbackComposablePreview() {
    PreviewContainer {
        FeedbackComposable()
    }
}
