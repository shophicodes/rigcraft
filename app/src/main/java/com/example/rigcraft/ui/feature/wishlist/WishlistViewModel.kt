package com.example.rigcraft.ui.feature.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository
): ViewModel() {
    val uiState: StateFlow<WishlistUiState> = wishlistRepository.getWishlistItems()
        .map { items -> WishlistUiState(items = items, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WishlistUiState(isLoading = true)
        )

    fun removeFromWishlist(productId: String) {
        viewModelScope.launch {
            wishlistRepository.removeFromWishlist(productId)
        }
    }
}