package com.demax.feature.destructions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.destructions.domain.DestructionsRepository
import com.demax.feature.destructions.domain.model.DestructionDomainModel
import com.demax.feature.destructions.domain.model.FilterOptionDomainModel
import com.demax.feature.destructions.domain.model.SortOptionDomainModel
import com.demax.feature.destructions.domain.model.SortingTypeDomainModel
import com.demax.feature.destructions.mvi.DestructionsIntent
import com.demax.feature.destructions.mvi.DestructionsSideEffect
import com.demax.feature.destructions.mvi.DestructionsState
import kotlinx.coroutines.launch

internal class DestructionsViewModel(
    private val repository: DestructionsRepository,
) : ViewModel(),
    Mvi<DestructionsState, DestructionsIntent, DestructionsSideEffect> by
    createMviDelegate(
        DestructionsState(
            sorts = listOf(
                SortOptionDomainModel(type = SortingTypeDomainModel.BY_PRIORITY, isSelected = true),
                SortOptionDomainModel(type = SortingTypeDomainModel.BY_DATE, isSelected = false),
            ),
            filters = listOf(),
            isAdministrator = false,
            destructions = listOf()
        )
    ) {

    init {
        viewModelScope.launch {
            repository.getDestructions().onSuccess { destructions ->
                updateUiState {
                    copy(
                        destructions = destructions.sortedByDescending { it.priority },
                        filters = getFilters(destructions)
                    )
                }
            }
        }
    }

    private fun getFilters(destructions: List<DestructionDomainModel>): List<FilterOptionDomainModel> {
        val statuses = destructions.map { it.status }.distinct()
        val specializations = destructions.flatMap { it.specializations }.distinct()
        val buildingTypes = destructions
            .map { it.buildingType }
            .distinct()

        val statusFilters = statuses.map { status ->
            FilterOptionDomainModel(
                type = FilterOptionDomainModel.Type.Status(status),
                isSelected = false,
            )
        }
        val specializationFilters = specializations.map { specialization ->
            FilterOptionDomainModel(
                type = FilterOptionDomainModel.Type.Specialization(specialization),
                isSelected = false,
            )
        }
        val buildingTypeFilters = buildingTypes.map { buildingType ->
            FilterOptionDomainModel(
                type = FilterOptionDomainModel.Type.BuildingType(buildingType),
                isSelected = false,
            )
        }

        return statusFilters + specializationFilters + buildingTypeFilters
    }

    override fun onIntent(intent: DestructionsIntent) {
        when (intent) {
            is DestructionsIntent.SortClicked -> onSortClicked()
            is DestructionsIntent.SortItemClicked -> onSortItemClicked(intent)
            is DestructionsIntent.FilterClicked -> onFilterClicked()
            is DestructionsIntent.FilterOptionClicked -> onFilterOptionClicked(intent)
            is DestructionsIntent.AddDestructionClicked -> onAddDestructionClicked()
            is DestructionsIntent.DestructionClicked -> onDestructionClicked(intent)
        }
    }

    private fun onSortClicked() {
        viewModelScope.emitSideEffect(DestructionsSideEffect.OpenSortBottomSheet)
    }

    private fun onSortItemClicked(intent: DestructionsIntent.SortItemClicked) {
        updateUiState {
            copy(
                sorts = sorts.map { it.copy(isSelected = it.type == intent.type) },
                destructions = destructions.sortedByDescending {
                    when (intent.type) {
                        SortingTypeDomainModel.BY_PRIORITY -> it.priority
                        SortingTypeDomainModel.BY_DATE -> it.destructionDate.toEpochDays()
                    }
                }
            )
        }
    }

    private fun onFilterClicked() {
        viewModelScope.emitSideEffect(DestructionsSideEffect.OpenFilterBottomSheet)
    }

    private fun onFilterOptionClicked(intent: DestructionsIntent.FilterOptionClicked) {
        updateUiState {
            copy(filters = filters.map { filter ->
                if (filter.type == intent.type) {
                    filter.copy(isSelected = !filter.isSelected)
                } else {
                    filter
                }
            })
        }
    }

    private fun onAddDestructionClicked() {

    }

    private fun onDestructionClicked(intent: DestructionsIntent.DestructionClicked) {
        viewModelScope.emitSideEffect(DestructionsSideEffect.OpenDestructionDetails(intent.id))
    }
}