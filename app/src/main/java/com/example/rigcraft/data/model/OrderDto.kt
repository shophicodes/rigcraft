package com.example.rigcraft.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class OrderDto(
    @DocumentId val orderId: String = "",
    val userId: String = "",
    val items: List<CartItemDto> = emptyList(),
    val quantity: Int = 0,
    val totalAmount: Double = 0.0,
    val orderStatus: String = "",
    val shippingAddress: AddressDto? = null,
    val paymentMethod: String = "SIMULATED_CARD",
    @ServerTimestamp val createdAt: Timestamp? = null
)

