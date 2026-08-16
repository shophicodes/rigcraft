package com.example.rigcraft.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class ProductDto(
    @DocumentId val id: String = "",
    val title: String = "",
    val brand: String = "",
    val categoryId: String = "",
    val subcategoryId: String = "",
    val price: Double = 0.0,
    val discountPercent: Int = 0,
    val inStock: Boolean = false,
    val stockQuantity: Int = 0,
    val images: List<String> = emptyList(),
    val specifications: Map<String, String> = emptyMap(),
    val ratingAverage: Double = 0.0,
    val reviewCount: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null
)
