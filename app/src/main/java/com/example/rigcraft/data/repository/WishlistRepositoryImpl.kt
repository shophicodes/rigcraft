package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.WishlistRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): WishlistRepository {
    private val currentUserId: String
        get() = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    private fun getWishlistCollection() =
        firestore.collection("users").document(currentUserId).collection("wishlist")

    override fun getWishlistItems(): Flow<List<ProductDto>> = callbackFlow {
        val subscription = getWishlistCollection()
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val products = snapshot.toObjects(ProductDto::class.java)
                    trySend(products)
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun isProductInWishlist(productId: String): Flow<Boolean> = callbackFlow {
        val subscription = getWishlistCollection().document(productId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                trySend(snapshot?.exists() == true)
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun addToWishlist(product: ProductDto) {
        getWishlistCollection().document(product.id).set(product).await()
    }

    override suspend fun removeFromWishlist(productId: String) {
        getWishlistCollection().document(productId).delete().await()
    }
}