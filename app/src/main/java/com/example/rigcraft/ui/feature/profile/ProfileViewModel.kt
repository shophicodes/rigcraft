package com.example.rigcraft.ui.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.ProfileRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authFirebase: FirebaseAuth,
    private val authRepository: AuthRepository
): ViewModel() {
    private val currentUserId: String get() = authFirebase.currentUser?.uid ?: ""

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = authFirebase.currentUser
        _uiState.update {
            it.copy(
                userName = user?.displayName ?: "Korisnik",
                userEmail = user?.email ?: ""
            )
        }
    }

    fun selectTab(index: Int) {
        _uiState.update {
            it.copy(selectedTab = index)
        }
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            val res = profileRepository.updateName(newName)
            if (res is Resource.Success) {
                _uiState.update { it.copy(userName = newName, showEditNameDialog = false, message = "Ime uspešno promenjeno!") }
            } else if (res is Resource.Error) {
                _uiState.update { it.copy(errorMessage = res.message) }
            }
        }
    }
    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            val res = profileRepository.updatePassword(newPassword)
            if (res is Resource.Success) {
                _uiState.update { it.copy(showEditPasswordDialog = false, message = "Lozinka uspešno promenjena!") }
            } else if (res is Resource.Error) {
                _uiState.update { it.copy(errorMessage = res.message) }
            }
        }
    }

    fun deleteAccount(onDeleted: () -> Unit) {
        viewModelScope.launch {
            val res = profileRepository.deleteAccount()
            if (res is Resource.Success) onDeleted()
            else if (res is Resource.Error) _uiState.update { it.copy(errorMessage = res.message) }
        }
    }

    fun toggleNameDialog(show: Boolean) {
        _uiState.update {
            it.copy(showEditNameDialog = show)
        }
    }
    fun togglePasswordDialog(show: Boolean) {
        _uiState.update {
            it.copy(showEditPasswordDialog = show)
        }
    }
    fun toggleDeleteDialog(show: Boolean) {
        _uiState.update {
            it.copy(showDeleteAccountDialog = show)
        }
    }

    fun clearMessages() = _uiState.update { it.copy(message = null, errorMessage = null) }
}