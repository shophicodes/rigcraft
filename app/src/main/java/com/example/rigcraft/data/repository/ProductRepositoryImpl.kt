package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): ProductRepository {

    // Fetch categories, sorted by name
    override fun getCategories(): Flow<Resource<List<CategoryDto>>> = flow {
        emit(Resource.Loading)
        try {
            val snapshot = firestore.collection("categories")
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .await()
            val categories = snapshot.toObjects(CategoryDto::class.java)
            emit(Resource.Success(categories))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch categories"))
        }
    }

    // Fetch recently added products
    override fun getRecentProducts(): Flow<Resource<List<ProductDto>>> = flow {
        emit(Resource.Loading)
        try {
            val snapshot = firestore.collection("products")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(6)
                .get()
                .await()
            val products = snapshot.toObjects(ProductDto::class.java)
            emit(Resource.Success(products))
        }
        catch(e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch recent products"))
        }
    }

    // Fetch products that are on sale
    override fun getProductsOnSale(): Flow<Resource<List<ProductDto>>> = flow {
        emit(Resource.Loading)
        try {
            val snapshot = firestore.collection("products")
                .whereGreaterThan("discountPercent", 0)
                .limit(6)
                .get()
                .await()
            val products = snapshot.toObjects(ProductDto::class.java)
            emit(Resource.Success(products))
        }
        catch(e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch recent products"))
        }
    }

    // Fetch products by category
    override fun getProductsByCategory(categoryId: String): Flow<Resource<List<ProductDto>>> = flow {
        emit(Resource.Loading)
        try {
            val snapshot = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()
            val products = snapshot.toObjects(ProductDto::class.java)
            emit(Resource.Success(products))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to fetch products for $categoryId"))
        }
    }

    // Filter products by category, subcategory, price (for all products)
    override fun getFilteredProducts(
        categoryId: String?,
        subcategoryId: String?,
        minPrice: Double?,
        maxPrice: Double?
    ): Flow<Resource<List<ProductDto>>> = flow {
        emit(Resource.Loading)
        try {
            var query: Query = firestore.collection("products")

            // Append filters conditionally
            if (!categoryId.isNullOrEmpty()) {
                query = query.whereEqualTo("categoryId", categoryId)
            }
            if (!subcategoryId.isNullOrEmpty()) {
                query = query.whereEqualTo("subcategoryId", subcategoryId)
            }
            if (minPrice != null) {
                query = query.whereGreaterThanOrEqualTo("price", minPrice)
            }
            if (maxPrice != null) {
                query = query.whereLessThanOrEqualTo("price", maxPrice)
            }

            val snapshot = query.get().await()
            val products = snapshot.toObjects(ProductDto::class.java)
            emit(Resource.Success(products))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to apply filters"))
        }
    }

    // Filter products within same category by specifications
    override fun getProductsBySpecs(
        categoryId: String?,
        specFilters: Map<String, String>
    ): Flow<Resource<List<ProductDto>>> = flow {
        emit(Resource.Loading)
        try {
            var query: Query = firestore.collection("products")

            if (!categoryId.isNullOrEmpty()) {
                query = query.whereEqualTo("categoryId", categoryId)
            }

            // Query map fields dynamically using dot notation
            specFilters.forEach { (specKey, specValue) ->
                if (specValue.isNotEmpty()) {
                    query = query.whereEqualTo("specifications.$specKey", specValue)
                }
            }

            val snapshot = query.get().await()
            val products = snapshot.toObjects(ProductDto::class.java)
            emit(Resource.Success(products))
        }
        catch(e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to filter products by specifications"))
        }
    }

    // Fetch product by its ID
    override fun getProductById(productId: String): Flow<Resource<ProductDto?>> = flow {
        emit(Resource.Loading)
        try {
            val snapshot = firestore.collection("products")
                .document(productId)
                .get()
                .await()
            val product = snapshot.toObject(ProductDto::class.java)
            emit(Resource.Success(product))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to get product by ID"))
        }
    }
}