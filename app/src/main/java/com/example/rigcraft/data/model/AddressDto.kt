package com.example.rigcraft.data.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class AddressDto(
    val addressId: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val street: String = "",
    val city: String = "",
    val zip: String = "",
    val isDefault: Boolean = false
)
