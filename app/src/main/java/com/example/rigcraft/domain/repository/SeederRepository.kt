package com.example.rigcraft.domain.repository

import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface SeederRepository {
    suspend fun seedData(): Flow<Resource<String>>
}
