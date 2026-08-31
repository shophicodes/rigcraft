package com.example.rigcraft.ui.feature.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.data.model.OrderDto
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.domain.repository.OrderRepository
import com.example.rigcraft.domain.repository.ProductRepository
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
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
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
                val cartItems = if (cartResource is Resource.Success) cartResource.data else emptyList()
                val addresses = if (addressResource is Resource.Success) addressResource.data else emptyList()
                
                val subtotal = cartItems.sumOf { it.price * it.quantity }
                val shipping = if (subtotal > 10000.0 || cartItems.isEmpty()) 0.0 else 300.0
                val finalTotal = subtotal + shipping

                val selectedAddress = if (addresses.size == 1) addresses.first() else null

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cartItems = cartItems,
                        addresses = addresses,
                        totalPrice = finalTotal,
                        selectedAddress = selectedAddress,
                        errorMessage = null
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
        val currentState = _uiState.value
        val userId = authRepository.getCurrentUser() ?: return
        val address = currentState.selectedAddress ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val order = OrderDto(
                userId = userId,
                items = currentState.cartItems,
                quantity = currentState.cartItems.sumOf { it.quantity },
                totalAmount = currentState.totalPrice,
                shippingAddress = address,
                orderStatus = "PLACED",
                paymentMethod = "CASH_ON_DELIVERY"
            )

            try {
                // 1. Save order
                orderRepository.saveOrder(order)
                
                // 2. Update inventory
                currentState.cartItems.forEach { item ->
                    productRepository.updateProductStock(item.productId, -item.quantity)
                }
                
                // 3. Clear cart
                cartRepository.clearCart(userId)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        orderPlacedSuccessfully = true,
                        errorMessage = null
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Greška pri naručivanju"
                    ) 
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