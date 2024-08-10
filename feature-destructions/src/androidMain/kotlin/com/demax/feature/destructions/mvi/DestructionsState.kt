package com.demax.feature.destructions.mvi

import com.demax.feature.destructions.domain.model.DestructionDomainModel
import com.demax.feature.destructions.domain.model.FilterOptionDomainModel
import com.demax.feature.destructions.domain.model.SortOptionDomainModel

data class DestructionsState(
    val sorts: List<SortOptionDomainModel>,
    val filters: List<FilterOptionDomainModel>,
    val isAdministrator: Boolean,
    val destructions: List<DestructionDomainModel>,
) {
    val visibleDestructions: List<DestructionDomainModel> = run {
        destructions.filter { destruction ->
            val selectedFilters = filters.filter { it.isSelected }

            val selectedBuildingTypes =
                selectedFilters.mapNotNull { (it.type as? FilterOptionDomainModel.Type.BuildingType)?.buildingType }
            val selectedStatuses =
                selectedFilters.mapNotNull { (it.type as? FilterOptionDomainModel.Type.Status)?.status }
            val selectedSpecializations =
                selectedFilters.mapNotNull { (it.type as? FilterOptionDomainModel.Type.Specialization)?.specialization }

            (destruction.buildingType in selectedBuildingTypes || selectedBuildingTypes.isEmpty()) &&
                (destruction.status in selectedStatuses || selectedStatuses.isEmpty()) &&
                (destruction.specializations.intersect(selectedSpecializations.toSet())
                    .isNotEmpty() || selectedSpecializations.isEmpty())
        }
    }
}
