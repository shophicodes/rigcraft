package com.example.rigcraft.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val currentUserId: String?
        get() = authRepository.getCurrentUser()

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            authRepository.getAuthStateFlow()
                .flatMapLatest { userId ->
                    if (userId == null) {
                        flowOf(Resource.Error("Korisniku nije dozvoljen pristup"))
                    } else {
                        cartRepository.getCartItems(userId)
                    }
                }
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            val items = result.data
                            val subtotal = items.sumOf { it.price * it.quantity }
                            val shipping = if (subtotal > 10000.0 || items.isEmpty()) 0.0 else 300.0
                            val total = subtotal + shipping

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    cartItems = items,
                                    subtotal = subtotal,
                                    shippingFee = shipping,
                                    total = total,
                                    errorMessage = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    cartItems = emptyList(),
                                    errorMessage = result.message
                                )
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    fun updateQuantity(cartItemId: String, currentQuantity: Int, n: Int) {
        val userId = currentUserId ?: return
        val newQuantity = currentQuantity + n
        if(newQuantity <= 0) {
            removeItem(cartItemId)
            return
        }
        viewModelScope.launch {
            cartRepository.updateQuantity(userId, cartItemId, newQuantity)
        }
    }

    fun removeItem(cartItemId: String) {
        val userId = currentUserId ?: return
        viewModelScope.launch {
            cartRepository.removeCartItem(userId, cartItemId)
        }
    }
}