package com.example.rigcraft.ui.feature.catalog

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto

data class CatalogUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryDto> = emptyList(),
    val selectedCategoryId: String? = null,
    val selectedSubcategoryId: String? = null,
    val products: List<ProductDto> = emptyList(),
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val selectedSortOption: SortOption = SortOption.NEWEST,
    val errorMessage: String? = null
)
