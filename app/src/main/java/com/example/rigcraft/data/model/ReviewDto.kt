package com.example.rigcraft.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class ReviewDto(
    @DocumentId val reviewId: String = "",
    val userId: String = "",
    val userName: String = "",
    val rating: Float = 0.0f,
    val comment: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)
