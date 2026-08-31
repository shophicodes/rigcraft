package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.ProductDto
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    fun getWishlistItems(): Flow<List<ProductDto>>
    fun isProductInWishlist(productId: String): Flow<Boolean>
    suspend fun addToWishlist(product: ProductDto)
    suspend fun removeFromWishlist(productId: String)
}