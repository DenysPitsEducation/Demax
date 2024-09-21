package com.demax.feature.responses.mvi

import com.demax.feature.responses.domain.model.FilterOptionDomainModel
import com.demax.feature.responses.domain.model.ResponseDomainModel
import com.demax.feature.responses.domain.model.toEnum

data class ResponsesState(
    val filters: List<FilterOptionDomainModel>,
    val responses: List<ResponseDomainModel>,
) {
    val visibleResponses: List<ResponseDomainModel> = run {
        val selectedFilters = filters.filter { it.isSelected }

        val selectedTypes =
            selectedFilters.mapNotNull { (it.type as? FilterOptionDomainModel.Type.ResponseType)?.type }

        responses.filter { response ->
            response.type.toEnum() in selectedTypes || selectedTypes.isEmpty()
        }
    }
}
