package com.example.rigcraft.ui.feature.order

import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.data.model.CartItemDto

data class OrderUiState(
    val addresses: List<AddressDto> = emptyList(),
    val selectedAddress: AddressDto? = null,
    val cartItems: List<CartItemDto> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val orderPlacedSuccessfully: Boolean = false,
    val placedOrderId: String? = null,
    val errorMessage: String? = null
)
