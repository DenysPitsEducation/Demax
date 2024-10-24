package com.demax.feature.destruction.edit.mapper

import com.demax.core.domain.model.BuildingTypeDomainModel
import com.demax.core.ui.mapper.BuildingTypeUiMapper
import com.demax.feature.destruction.edit.domain.model.DestructionEditDomainModel
import com.demax.feature.destruction.edit.domain.model.PredictionSwitchDomainModel
import com.demax.feature.destruction.edit.domain.model.NeedsDomainModel
import com.demax.feature.destruction.edit.model.BuildingTypeUiModel
import com.demax.feature.destruction.edit.model.DestructionEditUiModel
import com.demax.feature.destruction.edit.model.PredictionSwitchUiModel
import com.demax.feature.destruction.edit.model.NeedsUiModel
import com.demax.feature.destruction.edit.mvi.DestructionEditState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.byUnicodePattern

internal class DestructionEditUiMapper(
    private val buildingTypeUiMapper: BuildingTypeUiMapper,
) {

    fun mapToUiModel(state: DestructionEditState): DestructionEditUiModel? = state.run {
        return state.domainModel?.toUiModel()
    }

    private fun DestructionEditDomainModel.toUiModel(): DestructionEditUiModel {
        val dateFormatter = LocalDate.Format { byUnicodePattern("dd/MM/yyyy") }
        val timeFormatter = LocalTime.Format { byUnicodePattern("HH:mm") }
        return DestructionEditUiModel(
            image = imageUrl ?: imageFile?.uri,
            buildingType = buildingType?.let { buildingTypeUiMapper.mapToUiModel(it) }.orEmpty(),
            dropDownBuildingTypes = BuildingTypeDomainModel.entries.map {
                val title = buildingTypeUiMapper.mapToUiModel(it)
                BuildingTypeUiModel(it, title)
            },
            address = address.orEmpty(),
            predictionSwitch = predictionSwitch?.toUiModel(),
            numberOfApartments = numberOfApartments?.toString().orEmpty(),
            apartmentsSquare = apartmentsSquare?.toString().orEmpty(),
            destroyedFloors = destroyedFloors?.toString().orEmpty(),
            destroyedSections = destroyedSections?.toString().orEmpty(),
            destroyedPercentage = destroyedPercentage?.toString().orEmpty(),
            isArchitecturalMonument = isArchitecturalMonument,
            containsDangerousSubstances = containsDangerousSubstances,
            destructionDate = destructionDate?.let { dateFormatter.format(it) }.orEmpty(),
            destructionTime = destructionTime?.let { timeFormatter.format(it) }.orEmpty(),
            description = description.orEmpty(),
            createButtonEnabled = isCreateButtonEnabled(needsDomainModel),
        )
    }

    private fun DestructionEditDomainModel.isCreateButtonEnabled(needsDomainModel: NeedsDomainModel?): Boolean {
        val isAnyResourceSelected = needsDomainModel != null && (needsDomainModel.helpPackages > 0 || needsDomainModel.specializations.any { it.quantity > 0 })
        return buildingType != null && address != null && destructionDate != null && description != null && isAnyResourceSelected
    }

    private fun PredictionSwitchDomainModel.toUiModel(): PredictionSwitchUiModel {
        return PredictionSwitchUiModel(isChecked = isChecked)
    }

    fun mapToNeedsBottomSheetUiModel(model: NeedsDomainModel): NeedsUiModel =
        model.run {
            NeedsUiModel(
                helpPackages = helpPackages,
                specializations = specializations.map {
                    NeedsUiModel.SpecializationUiModel(name = it.name, quantity = it.quantity)
                }
            )
        }
}