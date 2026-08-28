package com.example.rigcraft.ui.feature.search

import com.example.rigcraft.data.model.ProductDto

data class SearchUiState(
    val searchQuery: String = "",
    val products: List<ProductDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
