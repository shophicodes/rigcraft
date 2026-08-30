package com.example.rigcraft.data.repository

import com.example.rigcraft.domain.repository.ProfileRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
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
}