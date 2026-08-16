package com.example.rigcraft.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class CategoryDto(
    @DocumentId val categoryId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val parentCategory: String? = null // if top-level = null, if subcategory = parent ID
)
