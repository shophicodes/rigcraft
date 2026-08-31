package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.OrderDto
import com.example.rigcraft.domain.repository.OrderRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): OrderRepository {
    private val currentUserId: String
        get() = auth.currentUser?.uid ?: throw IllegalStateException("User must be logged in to access orders")

    private fun getOrdersCollection(userId: String) =
        firestore.collection("users").document(userId).collection("orders")

    override suspend fun checkout(order: OrderDto): Resource<Unit> {
        return try {
            val userId = currentUserId
            firestore.runTransaction { transaction ->
                // 1. Validate stock and prepare updates
                order.items.forEach { item ->
                    val productRef = firestore.collection("products").document(item.productId)
                    val snapshot = transaction.get(productRef)
                    val currentStock = snapshot.getLong("stockQuantity") ?: 0L
                    
                    if (currentStock < item.quantity) {
                        throw Exception("Nedovoljno zaliha za proizvod: ${item.title}")
                    }
                    
                    val newStock = currentStock - item.quantity
                    transaction.update(productRef, "stockQuantity", newStock)
                    transaction.update(productRef, "inStock", newStock > 0)
                }

                // 2. Save order
                val orderRef = if (order.orderId.isEmpty()) {
                    getOrdersCollection(userId).document()
                } else {
                    getOrdersCollection(userId).document(order.orderId)
                }

                val orderToSave = order.copy(
                    orderId = orderRef.id,
                    userId = userId
                )
                transaction.set(orderRef, orderToSave)
            }.await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Greška pri obradi porudžbine")
        }
    }

    override fun getOrdersForUser(userId: String): Flow<List<OrderDto>> = callbackFlow {
        val subscription = getOrdersCollection(userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val orders = snapshot.toObjects(OrderDto::class.java)
                    trySend(orders)
                }
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun getOrderById(orderId: String): OrderDto? {
        val userId = currentUserId
        val snapshot = getOrdersCollection(userId)
            .document(orderId)
            .get()
            .await()

        return snapshot.toObject(OrderDto::class.java)
    }
}