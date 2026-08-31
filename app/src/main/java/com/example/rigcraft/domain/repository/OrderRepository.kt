package com.example.rigcraft.domain.repository

import com.example.rigcraft.data.model.OrderDto
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun saveOrder(order: OrderDto)
    fun getOrdersForUser(userId: String): Flow<List<OrderDto>>
    suspend fun getOrderById(orderId: String): OrderDto?
}