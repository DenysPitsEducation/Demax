package com.demax.feature.responses.domain.model

data class FilterOptionDomainModel(
    val type: Type,
    val isSelected: Boolean,
) {
    sealed class Type {
        data class ResponseType(val type: ResponseTypeEnumDomainModel) : Type()
    }
}
