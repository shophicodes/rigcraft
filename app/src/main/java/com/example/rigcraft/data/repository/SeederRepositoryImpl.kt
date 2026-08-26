package com.example.rigcraft.data.repository

import com.example.rigcraft.domain.repository.SeederRepository
import com.example.rigcraft.util.MockDataSeeder
import com.example.rigcraft.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SeederRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SeederRepository {

    override suspend fun seedData(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            val batch = firestore.batch()

            // Seed Categories
            MockDataSeeder.categories.forEach { category ->
                val docRef = firestore.collection("categories").document(category.categoryId)
                batch.set(docRef, category)
            }

            // Seed Products
            MockDataSeeder.products.forEach { product ->
                val docRef = firestore.collection("products").document(product.id)
                batch.set(docRef, product)
            }

            batch.commit().await()
            emit(Resource.Success("Successfully populated Firestore with categories & products!"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to seed database"))
        }
    }
}
