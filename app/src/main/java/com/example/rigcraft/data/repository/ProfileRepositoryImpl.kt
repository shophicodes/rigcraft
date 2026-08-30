package com.example.rigcraft.data.repository

import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.domain.repository.ProfileRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): ProfileRepository {
    override suspend fun updateName(newName: String): Resource<Unit> {
        return try {
            val user = auth.currentUser ?: return Resource.Error("Korisnik nije prijavljen")
            val updates = UserProfileChangeRequest.Builder().setDisplayName(newName).build()
            user.updateProfile(updates).await()
            Resource.Success(Unit)
        }
        catch(e: Exception) {
            if (e is CancellationException) throw e
            Resource.Error(e.message ?: "Greška pri promeni imena")
        }
    }

    override suspend fun updatePassword(newPassword: String): Resource<Unit> {
        return try {
            val user = auth.currentUser ?: return Resource.Error("Korisnik nije prijavljen")
            user.updatePassword(newPassword).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Resource.Error(e.message ?: "Greška pri promeni lozinke")
        }
    }

    override suspend fun deleteAccount(): Resource<Unit> {
        return try {
            val user = auth.currentUser ?: return Resource.Error("Korisnik nije prijavljen")
            user.delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Resource.Error(e.message ?: "Greška pri brisanju naloga")
        }
    }

    // Address actions
    override fun getAddresses(userId: String): Flow<Resource<List<AddressDto>>> {
        return firestore.collection("users")
            .document(userId)
            .collection("addresses")
            .snapshots()
            .map { snapshot ->
                try {
                    Resource.Success(snapshot.toObjects(AddressDto::class.java))
                } catch (e: Exception) {
                    Resource.Error(e.message ?: "Greška pri učitavanju adresa za dostavu")
                }
            }
    }

    override suspend fun saveAddress(userId: String, address: AddressDto): Resource<Unit> {
        return try {
            val collection = firestore.collection("users").document(userId).collection("addresses")
            if (address.addressId.isEmpty()) {
                val newDoc = collection.document()
                newDoc.set(address.copy(addressId = newDoc.id)).await()
            } else {
                collection.document(address.addressId).set(address).await()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Greška pri čuvanju adrese za dostavu")
        }
    }

    override suspend fun deleteAddress(userId: String, addressId: String): Resource<Unit> {
        return try {
            firestore.collection("users").document(userId)
                .collection("addresses").document(addressId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Greška pri brisanju adrese za dostavu")
        }
    }
}