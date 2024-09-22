package com.demax.feature.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demax.core.domain.repository.UserRepository
import com.demax.core.mvi.Mvi
import com.demax.core.mvi.createMviDelegate
import com.demax.feature.resources.domain.ResourcesRepository
import com.demax.feature.resources.domain.model.FilterOptionDomainModel
import com.demax.feature.resources.domain.model.ResourceDomainModel
import com.demax.feature.resources.mvi.ResourcesIntent
import com.demax.feature.resources.mvi.ResourcesSideEffect
import com.demax.feature.resources.mvi.ResourcesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResourcesViewModel(
    private val repository: ResourcesRepository,
    private val userRepository: UserRepository,
) : ViewModel(),
    Mvi<ResourcesState, ResourcesIntent, ResourcesSideEffect> by
    createMviDelegate(
        ResourcesState(
            searchInput = "",
            filters = listOf(),
            isAdministrator = false,
            resources = listOf(),
        )
    ) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserFlow().collect {
                updateUiState { copy(isAdministrator = it?.isAdministrator == true) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getResources().onSuccess { resources ->
                updateUiState {
                    copy(
                        resources = resources,
                        filters = getFilters(resources),
                    )
                }
            }
        }
    }

    private fun getFilters(resources: List<ResourceDomainModel>): List<FilterOptionDomainModel> {
        val statuses = resources.map { it.status }.distinct()
        val categories = resources
            .map { it.category }
            .distinct()

        val statusFilters = statuses.map { status ->
            FilterOptionDomainModel(
                type = FilterOptionDomainModel.Type.Status(status),
                isSelected = false,
            )
        }
        val categoryFilters = categories.map { category ->
            FilterOptionDomainModel(
                type = FilterOptionDomainModel.Type.Category(category),
                isSelected = false,
            )
        }

        return statusFilters + categoryFilters
    }

    override fun onIntent(intent: ResourcesIntent) {
        when (intent) {
            is ResourcesIntent.SearchInputChanged -> onSearchInputChanged(intent)
            is ResourcesIntent.FilterClicked -> onFilterClicked()
            is ResourcesIntent.FilterOptionClicked -> onFilterOptionClicked(intent)
            is ResourcesIntent.AddResourceClicked -> onAddResourceClicked()
            is ResourcesIntent.ResourceClicked -> onResourceClicked(intent)
        }
    }

    private fun onSearchInputChanged(intent: ResourcesIntent.SearchInputChanged) {
        updateUiState { copy(searchInput = intent.input) }
    }

    private fun onFilterClicked() {
        viewModelScope.emitSideEffect(ResourcesSideEffect.OpenFilterBottomSheet)
    }

    private fun onFilterOptionClicked(intent: ResourcesIntent.FilterOptionClicked) {
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

    private fun onAddResourceClicked() {
        viewModelScope.emitSideEffect(ResourcesSideEffect.OpenResourceEdit)
    }

    private fun onResourceClicked(intent: ResourcesIntent.ResourceClicked) {
        viewModelScope.emitSideEffect(ResourcesSideEffect.OpenResourceDetails(intent.id))
    }
}