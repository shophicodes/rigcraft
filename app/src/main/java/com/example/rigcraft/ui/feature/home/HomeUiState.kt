package com.example.rigcraft.ui.feature.home

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto

data class HomeUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryDto> = emptyList(),
    val featuredProducts: List<ProductDto> = emptyList(),
    val errorMessage: String? = null
)
