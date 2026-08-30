package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun updateName(newName: String): Resource<Unit>
    suspend fun updatePassword(newPassword: String): Resource<Unit>
    suspend fun deleteAccount(): Resource<Unit>

    fun getAddresses(userId: String): Flow<Resource<List<AddressDto>>>
    suspend fun saveAddress(userId: String, address: AddressDto): Resource<Unit>
    suspend fun deleteAddress(userId: String, addressId: String): Resource<Unit>
}