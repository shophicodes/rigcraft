package com.example.rigcraft.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val auth: FirebaseAuth
): ViewModel() {
    private val currentUserId: String
        get() = auth.currentUser?.uid ?: "guest_user"

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            cartRepository.getCartItems(currentUserId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        val items = result.data
                        val subtotal = items.sumOf { it.price * it.quantity }
                        val shipping = if (subtotal > 10000.0 || items.isEmpty()) 0.0 else 300.0
                        // By default, shipping cost = 350 RSD
                        // If a total price of cart items > 10000 RSD, keep shipping free
                        val total = subtotal + shipping

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                cartItems = items,
                                subtotal = subtotal,
                                shippingFee = shipping,
                                total = total
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateQuantity(cartItemId: String, currentQuantity: Int, n: Int) {
        val newQuantity = currentQuantity + n
        if(newQuantity <= 0) {
            removeItem(cartItemId)
            return
        }
        viewModelScope.launch {
            cartRepository.updateQuantity(currentUserId, cartItemId, newQuantity)
        }
    }

    fun removeItem(cartItemId: String) {
        viewModelScope.launch {
            cartRepository.removeCartItem(currentUserId, cartItemId)
        }
    }
}