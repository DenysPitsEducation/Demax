package com.demax.feature.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.demax.core.mvi.SideEffectLaunchedEffect
import com.demax.core.ui.LocalSnackbarHostState
import com.demax.core.ui.PreviewContainer
import com.demax.feature.profile.ProfileViewModel
import com.demax.feature.profile.mapper.ProfileUiMapper
import com.demax.feature.profile.model.ProfileUiModel
import com.demax.feature.profile.mvi.ProfileIntent
import com.demax.feature.profile.navigation.ProfileRouter
import org.koin.compose.koinInject

internal typealias OnUserInteraction = (ProfileIntent) -> Unit

@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel = koinInject()
    val router: ProfileRouter = koinInject()
    val mapper: ProfileUiMapper = koinInject()
    val state = viewModel.uiState.collectAsState().value
    val uiModel = mapper.mapToUiModel(state)
    val snackbarHostState = LocalSnackbarHostState.current

    SideEffectLaunchedEffect(
        sideEffectsFlow = viewModel.sideEffects,
    ) { sideEffect ->
        /*when (sideEffect) {
            is DestructionsSideEffect.OpenMainScreen -> router.openMainScreen(navController)
            is DestructionsSideEffect.OpenPasswordResetScreen -> router.openPasswordResetScreen(navController)
            is DestructionsSideEffect.OpenRegistrationScreen -> router.openRegistrationScreen(navController)
            is DestructionsSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(sideEffect.text)
        }*/
    }

    if (uiModel != null) {
        ProfileContent(model = uiModel, onUserInteraction = { viewModel.onIntent(it) })
    } else {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileContent(
    model: ProfileUiModel,
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
            text = "Профіль користувача",
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = model.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .border(1.dp, Color(0xFFA8A8A9), CircleShape)
                .clip(CircleShape)
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Персональна інформація",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = model.name,
            onValueChange = {

            },
            label = { Text(text = "ПІБ") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.email,
            onValueChange = {

            },
            label = { Text(text = "Електронна пошта") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.phoneNumber,
            onValueChange = {

            },
            label = { Text(text = "Номер телефону") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.address,
            onValueChange = {

            },
            label = { Text(text = "Домашня адреса") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = model.about,
            onValueChange = {

            },
            label = { Text(text = "Про себе") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                //onUserInteraction(LoginIntent.LoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Зберегти зміни")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Участь у роботі фонду",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Спеціалізації", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                model.specializations.forEach { specialization ->
                    SpecializationComposable(specialization)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Дата реєстрації", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = model.registrationDate)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFA8A8A9), RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = "Участь у волонтерській діяльності", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${model.helpsCount} рази")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                //onUserInteraction(LoginIntent.LoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Історія допомог")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SpecializationComposable(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .border(1.dp, Color(0xFF0E0808), RoundedCornerShape(4.dp))
            .background(Color(0xFFF3F3F3), RoundedCornerShape(4.dp))
            .padding(vertical = 2.dp, horizontal = 4.dp)
    ) {
        Text(text = text)
    }
}

internal fun createProfileUiModelMock() = ProfileUiModel(
    imageUrl = "https://picsum.photos/200/200",
    name = "Фонд Допомоги Постраждалим",
    email = "fund@gmail.com",
    phoneNumber = "380958311553",
    address = "м. Київ, вул. Володимирська 4",
    about = "Свою місію я вбачаю в активній волонтерській діяльності, що спрямована на підтримку обороноздатності країни",
    specializations = listOf("Психотерапія", "Керування вантажівкою"),
    registrationDate = "21/04/2023",
    helpsCount = 4,
)

@Composable
@Preview
private fun ProfileContentPreview() {
    PreviewContainer {
        ProfileContent(
            model = createProfileUiModelMock(),
            onUserInteraction = {},
        )
    }
}
