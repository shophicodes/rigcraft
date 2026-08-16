package com.example.rigcraft.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserDto(
    @DocumentId val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val savedAddresses: List<AddressDto> = emptyList(),
    val wishlist: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null
)
