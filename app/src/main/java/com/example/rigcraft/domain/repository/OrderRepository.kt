package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.OrderDto
import com.example.rigcraft.util.Resource
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun checkout(order: OrderDto): Resource<Unit>
    fun getOrdersForUser(userId: String): Flow<List<OrderDto>>
    suspend fun getOrderById(orderId: String): OrderDto?
}