package com.example.rigcraft.domain.repository

import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): String?
    fun isUserLoggedIn(): Boolean
    suspend fun login(email: String, pass: String): Flow<Resource<Boolean>>
    suspend fun register(email: String, displayName: String, pass: String): Flow<Resource<Boolean>>
    suspend fun logout()
}