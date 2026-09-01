package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCategories(): Flow<Resource<List<CategoryDto>>>
    fun getRecentProducts(): Flow<Resource<List<ProductDto>>>
    fun getProductsOnSale(): Flow<Resource<List<ProductDto>>>
    fun getFilteredProducts(
        categoryId: String?,
        subcategoryId: String?,
        minPrice: Double?,
        maxPrice: Double?
    ): Flow<Resource<List<ProductDto>>>
    fun getProductById(productId: String): Flow<Resource<ProductDto?>>
}