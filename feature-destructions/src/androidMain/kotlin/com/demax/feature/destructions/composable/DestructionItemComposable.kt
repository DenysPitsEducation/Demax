package com.demax.feature.destructions.composable

import android.icu.text.AlphabeticIndex.Bucket.LabelType
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.demax.core.ui.PreviewContainer
import com.demax.feature.destructions.model.DestructionItemUiModel
import com.demax.feature.destructions.mvi.DestructionsIntent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DestructionItemComposable(
    model: DestructionItemUiModel,
    onUserInteraction: OnUserInteraction,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onUserInteraction(DestructionsIntent.DestructionClicked(model.id))
            }
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = model.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .size(125.dp, 85.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Тип будівлі: ")
                        }
                        append(model.buildingType)
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Дата руйнування: ")
                        }
                        append(model.destructionDate)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Потреба в ресурсах:",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(
                        text = model.resourceProgress.percentage,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = model.resourceProgress.text,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { model.resourceProgress.progress.toFloat() },
                    modifier = Modifier.width(130.dp),
                    strokeCap = StrokeCap.Round,
                    color = model.resourceProgress.color,
                    trackColor = Color(0xFFE0E0E0),
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Потреба у волонтерах:",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Text(
                        text = model.volunteerProgress.percentage,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = model.volunteerProgress.text,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { model.volunteerProgress.progress.toFloat() },
                    modifier = Modifier.width(130.dp),
                    strokeCap = StrokeCap.Round,
                    color = model.volunteerProgress.color,
                    trackColor = Color(0xFFE0E0E0)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Спеціалізації:",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                model.specializations.forEach { specialization ->
                    LabelComposable(specialization, Color(0xFFF3F3F3))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Статус:",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            LabelComposable(model.status.text, model.status.background)
        }
    }
}

@Composable
private fun LabelComposable(
    text: String,
    background: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .border(1.dp, Color(0xFF0E0808), RoundedCornerShape(4.dp))
            .background(background, RoundedCornerShape(4.dp))
            .padding(vertical = 2.dp, horizontal = 4.dp)
    ) {
        Text(text = text)
    }
}

@Composable
@Preview
private fun DestructionItemComposablePreview() {
    PreviewContainer {
        val model = createDestructionItemUiModelsMock().first()
        DestructionItemComposable(model, {})
    }
}