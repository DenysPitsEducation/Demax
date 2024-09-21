package com.demax.feature.resources.mvi

import com.demax.feature.resources.domain.model.FilterOptionDomainModel
import com.demax.feature.resources.domain.model.ResourceDomainModel

data class ResourcesState(
    val searchInput: String,
    val filters: List<FilterOptionDomainModel>,
    val isAdministrator: Boolean,
    val resources: List<ResourceDomainModel>,
) {
    val visibleResources: List<ResourceDomainModel> = run {
        resources.filter { resource ->
            val selectedFilters = filters.filter { it.isSelected }

            val selectedCategories =
                selectedFilters.mapNotNull { (it.type as? FilterOptionDomainModel.Type.Category)?.category }
            val selectedStatuses =
                selectedFilters.mapNotNull { (it.type as? FilterOptionDomainModel.Type.Status)?.status }

            (resource.category in selectedCategories || selectedCategories.isEmpty()) &&
                (resource.status in selectedStatuses || selectedStatuses.isEmpty())
        }
    }
}
