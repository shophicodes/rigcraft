package com.example.rigcraft.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val productId: String = checkNotNull(savedStateHandle["productId"])

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DetailsUiEvent>()
    val eventFlow: SharedFlow<DetailsUiEvent> = _eventFlow.asSharedFlow()

    init {
        loadProduct()
    }

    fun retry() {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            productRepository.getProductById(productId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            product = result.data
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun incrementQuantity() {
        val current = _uiState.value.selectedQuantity
        val maxStock = _uiState.value.product?.stockQuantity ?: 1
        if (current < maxStock) {
            _uiState.value = _uiState.value.copy(selectedQuantity = current + 1)
        }
    }

    fun decrementQuantity() {
        val current = _uiState.value.selectedQuantity
        if (current > 1) {
            _uiState.value = _uiState.value.copy(selectedQuantity = current - 1)
        }
    }

    fun addToCart() {
        if (_uiState.value.isAddingToCart) return

        val userId = authRepository.getCurrentUser()
        if (userId == null) {
            _uiState.update { it.copy(cartErrorMessage = "Please sign in to add items to cart") }
            return
        }

        val product = _uiState.value.product ?: return
        val quantity = _uiState.value.selectedQuantity

        viewModelScope.launch {
            _uiState.update { it.copy(isAddingToCart = true) }
            val result = cartRepository.addToCart(
                userId = userId,
                product = product,
                quantity = quantity
            )

            when (result) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isAddingToCart = false,
                            userMessage = "Added $quantity item(s) to cart!"
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isAddingToCart = false,
                            cartErrorMessage = result.message ?: "Could not add to cart"
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(isAddingToCart = false) }
                }
            }
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null, cartErrorMessage = null, errorMessage = null) }
    }
}