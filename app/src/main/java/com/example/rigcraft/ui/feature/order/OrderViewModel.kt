package com.example.rigcraft.ui.feature.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.data.model.OrderDto
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.domain.repository.OrderRepository
import com.example.rigcraft.domain.repository.ProfileRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val profileRepository: ProfileRepository,
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private val _orders = MutableStateFlow<List<OrderDto>>(emptyList())
    val orders: StateFlow<List<OrderDto>> = _orders.asStateFlow()

    init {
        loadCheckoutData()
        loadOrderHistory()
    }

    private fun loadCheckoutData() {
        val userId = authRepository.getCurrentUser() ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                cartRepository.getCartItems(userId),
                profileRepository.getAddresses(userId)
            ) { cartResource, addressResource ->
                val cartItems = if (cartResource is Resource.Success) cartResource.data else _uiState.value.cartItems
                val addresses = if (addressResource is Resource.Success) addressResource.data else _uiState.value.addresses
                
                val errorMessage = when {
                    cartResource is Resource.Error -> cartResource.message
                    addressResource is Resource.Error -> addressResource.message
                    else -> null
                }

                val subtotal = cartItems.sumOf { it.price * it.quantity }
                val shipping = if (subtotal > 10000.0 || cartItems.isEmpty()) 0.0 else 300.0
                val finalTotal = subtotal + shipping

                val selectedAddress = if (addresses.size == 1) addresses.first() else _uiState.value.selectedAddress

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cartItems = cartItems,
                        addresses = addresses,
                        totalPrice = finalTotal,
                        selectedAddress = selectedAddress,
                        errorMessage = errorMessage
                    )
                }
            }.collect {}
        }
    }

    private fun loadOrderHistory() {
        val userId = authRepository.getCurrentUser() ?: return
        viewModelScope.launch {
            orderRepository.getOrdersForUser(userId).collect { orderList ->
                _orders.value = orderList
            }
        }
    }

    fun onAddressSelected(address: AddressDto?) {
        _uiState.update { it.copy(selectedAddress = address) }
    }

    fun placeOrder() {
        if (_uiState.value.isLoading) return
        
        val currentState = _uiState.value
        val userId = authRepository.getCurrentUser() ?: return
        val address = currentState.selectedAddress ?: return

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val order = OrderDto(
                userId = userId,
                items = currentState.cartItems,
                quantity = currentState.cartItems.sumOf { it.quantity },
                totalAmount = currentState.totalPrice,
                shippingAddress = address,
                orderStatus = "PLACED",
                paymentMethod = "CASH_ON_DELIVERY"
            )

            when (val result = orderRepository.checkout(order)) {
                is Resource.Success -> {
                    cartRepository.clearCart(userId)
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            orderPlacedSuccessfully = true,
                            errorMessage = null
                        ) 
                    }
                }
                is Resource.Error -> {
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            errorMessage = result.message
                        ) 
                    }
                }
                else -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
    
    fun resetOrderState() {
        _uiState.update { 
            it.copy(
                orderPlacedSuccessfully = false,
                errorMessage = null
            )
        }
    }
}