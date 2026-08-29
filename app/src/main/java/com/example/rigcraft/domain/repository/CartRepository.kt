package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.CartItemDto
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(userId: String): Flow<Resource<List<CartItemDto>>>
    suspend fun addToCart(
        userId: String,
        product: ProductDto,
        quantity: Int
    ): Resource<Unit>
    suspend fun updateQuantity(
        userId: String,
        cartItemId: String,
        newQuantity: Int
    ): Resource<Unit>
    suspend fun removeCartItem(userId: String, cartItemId: String): Resource<Unit>
    suspend fun clearCart(userId: String): Resource<Unit>
}