package com.example.rigcraft.ui.feature.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(WishlistUiState(isLoading = true))
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    private var wishlistJob: Job? = null

    init {
        loadWishlist()
    }

    private fun loadWishlist() {
        wishlistJob?.cancel()
        wishlistJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            wishlistRepository.getWishlistItems()
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Greška pri učitavanju liste želja") }
                }
                .collect { items ->
                    _uiState.update { it.copy(items = items, isLoading = false, errorMessage = null) }
                }
        }
    }

    fun retry() {
        loadWishlist()
    }

    fun removeFromWishlist(productId: String) {
        viewModelScope.launch {
            try {
                wishlistRepository.removeFromWishlist(productId)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Greška pri uklanjanju proizvoda iz liste želja") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}