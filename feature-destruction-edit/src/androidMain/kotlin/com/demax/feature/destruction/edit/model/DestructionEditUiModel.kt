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
    val destructionDate: String,
    val description: String,
    val createButtonEnabled: Boolean,
)