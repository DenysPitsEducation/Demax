package com.demax.feature.help.history.composable

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.demax.core.R
import com.demax.core.ui.PreviewContainer
import com.demax.feature.help.history.model.ResponseTypeUiModel
import com.demax.feature.help.history.model.HelpUiModel
import com.demax.feature.help.history.mvi.HelpHistoryIntent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HelpComposable(
    model: HelpUiModel,
    onUserInteraction: OnUserInteraction,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
    ) {
        if (model.type is ResponseTypeUiModel.Volunteer) {
            val destruction = model.type.destruction
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onUserInteraction(HelpHistoryIntent.DestructionClicked(destruction.id))
                    }
                    .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
            ) {
                AsyncImage(
                    model = destruction.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .border(1.dp, Color(0xFFA8A8A9), CircleShape)
                        .clip(CircleShape)
                        .size(50.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ill_no_photo),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Дата руйнування: ")
                            }
                            append(destruction.destructionDate)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Адреса: ")
                            }
                            append(destruction.address)
                        }
                    )
                }
            }
        }
        if (model.type is ResponseTypeUiModel.Resource) {
            val resources = model.type.resources
            resources.forEachIndexed { index, resource ->
                if (index != 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onUserInteraction(HelpHistoryIntent.ResourceClicked(resource.id))
                        }
                        .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                ) {
                    AsyncImage(
                        model = resource.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .border(1.dp, Color(0xFFA8A8A9), CircleShape)
                            .clip(CircleShape)
                            .size(50.dp),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.ill_no_photo),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Категорія: ")
                                }
                                append(resource.category)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Назва: ")
                                }
                                append(resource.name)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Кількість: ")
                                }
                                append(resource.quantity.toString())
                            }
                        )
                    }
                }
            }
        }
        if (model.type is ResponseTypeUiModel.Volunteer) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Спеціалізації:",
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    model.type.specializations.forEach { specialization ->
                        LabelComposable(specialization, Color(0xFFF3F3F3))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
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
private fun HelpComposableVolunteerPreview() {
    PreviewContainer {
        val model = createHelpUiModelsMock()[0]
        HelpComposable(model, {})
    }
}

@Composable
@Preview
private fun HelpComposableResourcePreview() {
    PreviewContainer {
        val model = createHelpUiModelsMock()[1]
        HelpComposable(model, {})
    }
}
