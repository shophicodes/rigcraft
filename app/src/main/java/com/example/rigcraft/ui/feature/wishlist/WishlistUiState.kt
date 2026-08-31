package com.example.rigcraft.ui.feature.wishlist

import com.example.rigcraft.data.model.ProductDto

data class WishlistUiState(
    val items: List<ProductDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
