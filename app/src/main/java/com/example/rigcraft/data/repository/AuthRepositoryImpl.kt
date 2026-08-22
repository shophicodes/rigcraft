package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.UserDto
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun getCurrentUser() = firebaseAuth.currentUser?.uid

    override fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null

    override suspend fun login(email: String, pass: String) = flow {
        emit(Resource.Loading)
        try {
            firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            emit(Resource.Success(true))
        }
        catch(e: Exception) {
            emit(Resource.Error(e.message ?: "Login error"))
        }
    }

    override suspend fun register(
        email: String,
        displayName: String,
        pass: String
    ) = flow {
        emit(Resource.Loading)
        try {
            val account = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()

            // Write user data to "users" collection in Firestore
            val uid = account.user?.uid ?: throw Exception("User creation failed")

            try {
                firestore.collection("users")
                    .document(uid)
                    .set(
                        UserDto(
                            uid = uid,
                            email = email,
                            displayName = displayName,
                            createdAt = Timestamp.now()
                        )
                    )
                    .await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                // Delete the newly created Firebase user if profile persistence fails
                account.user?.delete()?.await()
                throw e
            }
        }
        catch(e: Exception) {
            emit(Resource.Error(e.message ?: "Sign up error"))
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}