package com.demax.feature.responses.mapper

import androidx.compose.ui.graphics.Color
import com.demax.core.ui.mapper.ResourceCategoryUiMapper
import com.demax.feature.responses.domain.model.ProfileDomainModel
import com.demax.feature.responses.domain.model.ResponseDomainModel
import com.demax.feature.responses.domain.model.ResponseTypeDomainModel
import com.demax.feature.responses.model.DestructionUiModel
import com.demax.feature.responses.model.ProfileUiModel
import com.demax.feature.responses.model.ResourceUiModel
import com.demax.feature.responses.model.ResponseTypeUiModel
import com.demax.feature.responses.model.ResponseUiModel
import com.demax.feature.responses.model.ResponsesUiModel
import com.demax.feature.responses.model.StatusUiModel
import com.demax.feature.responses.mvi.ResponsesState
import com.google.api.ResourceProto.resource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class ResponsesUiMapper(
    val resourceCategoryMapper: ResourceCategoryUiMapper,
) {

    fun mapToUiModel(state: ResponsesState): ResponsesUiModel = state.run {
        return ResponsesUiModel(
            responseUiModels = responses.map { it.toUiModel() }
        )
    }

    private fun ResponseDomainModel.toUiModel(): ResponseUiModel {
        return ResponseUiModel(
            id = id,
            profile = profile.toUiModel(),
            type = type.toUiModel(),
            status = status.toUiModel(),
            showConfirmationButtons = status == ResponseDomainModel.StatusDomainModel.IDLE
        )
    }

    private fun ProfileDomainModel.toUiModel(): ProfileUiModel {
        return ProfileUiModel(id = id, name = name, imageUrl = imageUrl)
    }

    private fun ResponseTypeDomainModel.toUiModel(): ResponseTypeUiModel {
        return when (this) {
            is ResponseTypeDomainModel.Resource -> {
                ResponseTypeUiModel.Resource(
                    resources = resources.map { resource ->
                        ResourceUiModel(
                            id = resource.id,
                            imageUrl = resource.imageUrl,
                            category = resourceCategoryMapper.mapToUiModel(resource.category),
                            name = resource.name,
                            quantity = resource.quantity,
                        )
                    }
                )
            }

            is ResponseTypeDomainModel.Volunteer -> {
                val formatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
                ResponseTypeUiModel.Volunteer(
                    destruction = DestructionUiModel(
                        id = destruction.id,
                        imageUrl = destruction.imageUrl,
                        destructionDate = formatter.format(destruction.destructionDate),
                        address = destruction.address,
                    ),
                    specializations = specializations,
                )
            }
        }
    }

    private fun ResponseDomainModel.StatusDomainModel.toUiModel(): StatusUiModel {
        return when (this) {
            ResponseDomainModel.StatusDomainModel.IDLE -> StatusUiModel(
                text = "Очікує розгляду",
                background = Color(0xFFF2F487)
            )
            ResponseDomainModel.StatusDomainModel.APPROVED -> StatusUiModel(
                text = "Схвалено",
                background = Color(0xFFC6EB88)
            )
            ResponseDomainModel.StatusDomainModel.REJECTED -> StatusUiModel(
                text = "Відхилено",
                background = Color(0xFFE23538)
            )
        }
    }
}