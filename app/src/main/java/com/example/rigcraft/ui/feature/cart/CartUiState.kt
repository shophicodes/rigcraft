package com.example.rigcraft.ui.feature.cart

import com.example.rigcraft.data.model.CartItemDto

data class CartUiState(
    val isLoading: Boolean = false,
    val cartItems: List<CartItemDto> = emptyList(),
    val subtotal: Double = 0.0,
    val shippingFee: Double = 0.0,
    val total: Double = 0.0,
    val errorMessage: String? = null
)
