package com.example.rigcraft.ui.feature.details

import com.example.rigcraft.data.model.ProductDto

data class ProductDetailsUiState(
    val isLoading: Boolean = false,
    val product: ProductDto? = null,
    val selectedQuantity: Int = 1,
    val errorMessage: String? = null
)
