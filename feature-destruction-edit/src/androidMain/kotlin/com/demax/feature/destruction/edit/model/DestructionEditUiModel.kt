package com.demax.feature.destruction.edit.model

data class DestructionEditUiModel(
    val image: Any?,
    val address: String,
    val buildingType: String,
    val dropDownBuildingTypes: List<BuildingTypeUiModel>,
    val predictionSwitch: PredictionSwitchUiModel?,
    val numberOfApartments: String,
    val apartmentsSquare: String,
    val destroyedFloors: String,
    val destroyedSections: String,
    val destroyedPercentage: String,
    val isArchitecturalMonument: Boolean,
    val containsDangerousSubstances: Boolean,
    val destructionDate: String,
    val destructionTime: String,
    val description: String,
    val createButtonEnabled: Boolean,
)