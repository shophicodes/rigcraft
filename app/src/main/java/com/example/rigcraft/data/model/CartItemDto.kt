package com.example.rigcraft.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class CartItemDto(
    @DocumentId val itemId: String = "",
    val productId: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val image: String = ""
)
