package com.example.rigcraft.domain.repository

import com.example.rigcraft.util.Resource

interface ProfileRepository {
    suspend fun updateName(newName: String): Resource<Unit>
    suspend fun updateEmail(newEmail: String): Resource<Unit>
    suspend fun updatePassword(newPassword: String): Resource<Unit>
    suspend fun deleteAccount(): Resource<Unit>
}