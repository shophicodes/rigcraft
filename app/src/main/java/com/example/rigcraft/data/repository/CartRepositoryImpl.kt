package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.CartItemDto
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): CartRepository {
    override fun getCartItems(userId: String): Flow<Resource<List<CartItemDto>>> {
        return firestore.collection("carts")
            .document(userId)
            .collection("items")
            .snapshots()
            .map { snapshot ->
                try {
                    val items = snapshot.toObjects(CartItemDto::class.java)
                    Resource.Success(items)
                } catch (e: Exception) {
                    Resource.Error(e.message ?: "Failed to fetch cart")
                }
            }
    }

    override suspend fun updateQuantity(
        userId: String,
        cartItemId: String,
        newQuantity: Int
    ): Resource<Unit> {
        return try {
            firestore.collection("carts")
                .document(userId)
                .collection("items")
                .document(cartItemId)
                .update("quantity", newQuantity)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update quantity")
        }
    }

    override suspend fun removeCartItem(userId: String, cartItemId: String): Resource<Unit> {
        return try {
            firestore.collection("carts")
                .document(userId)
                .collection("items")
                .document(cartItemId)
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to remove item")
        }
    }

    override suspend fun clearCart(userId: String): Resource<Unit> {
        return try {
            val batch = firestore.batch()
            val snapshot = firestore.collection("carts")
                .document(userId)
                .collection("items")
                .get()
                .await()

            snapshot.documents.forEach { doc ->
                batch.delete(doc.reference)
            }
            batch.commit().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to clear cart")
        }
    }
}