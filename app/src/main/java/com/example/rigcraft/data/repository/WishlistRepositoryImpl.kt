package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.WishlistRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): WishlistRepository {
    private val currentUserId: String?
        get() = auth.currentUser?.uid

    private fun getWishlistCollection() =
        currentUserId?.let { firestore.collection("users").document(it).collection("wishlist") }

    override fun getWishlistItems(): Flow<List<ProductDto>> {
        val collection = getWishlistCollection() ?: return flowOf(emptyList())
        return callbackFlow {
            val subscription = collection
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
    }

    override fun isProductInWishlist(productId: String): Flow<Boolean> {
        val collection = getWishlistCollection() ?: return flowOf(false)
        return callbackFlow {
            val subscription = collection.document(productId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    trySend(snapshot?.exists() == true)
                }
            awaitClose { subscription.remove() }
        }
    }

    override suspend fun addToWishlist(product: ProductDto) {
        getWishlistCollection()?.document(product.id)?.set(product)?.await()
    }

    override suspend fun removeFromWishlist(productId: String) {
        getWishlistCollection()?.document(productId)?.delete()?.await()
    }
}