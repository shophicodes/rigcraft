package com.example.rigcraft.ui.feature.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.OrderDto
import com.example.rigcraft.domain.repository.OrderRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val orderId: String = checkNotNull(savedStateHandle["orderId"])

    private val _uiState = MutableStateFlow<Resource<OrderDto>>(Resource.Loading)
    val uiState: StateFlow<Resource<OrderDto>> = _uiState.asStateFlow()

    init {
        fetchOrder()
    }

    private fun fetchOrder() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading
            try {
                val order = orderRepository.getOrderById(orderId)
                if (order != null) {
                    _uiState.value = Resource.Success(order)
                } else {
                    _uiState.value = Resource.Error("Porudžbina nije pronađena")
                }
            } catch (e: Exception) {
                _uiState.value = Resource.Error(e.message ?: "Greška pri učitavanju porudžbine")
            }
        }
    }
}
