package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getCategories(): Flow<Resource<List<CategoryDto>>>
    fun getFeaturedProducts(): Flow<Resource<List<ProductDto>>>
    fun getProductsByCategory(categoryId: String): Flow<Resource<List<ProductDto>>>
    fun getFilteredProducts(
        categoryId: String?,
        subcategoryId: String?,
        minPrice: Double?,
        maxPrice: Double?
    ): Flow<Resource<List<ProductDto>>>
    fun getProductsBySpecs(
        categoryId: String?,
        specFilters: Map<String, String> // For example: mapOf("Socket" to "AM4", "VRAM" to "8GB")
    ): Flow<Resource<List<ProductDto>>>
}