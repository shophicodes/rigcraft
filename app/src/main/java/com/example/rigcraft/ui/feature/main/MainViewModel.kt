package com.example.rigcraft.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val auth: FirebaseAuth
): ViewModel() {
    val cartItemCount: StateFlow<Int> = auth.currentUser?.uid.let { userId ->
        if (userId != null) {
            cartRepository.getCartItems(userId)
                .map { result ->
                    if (result is Resource.Success) {
                        result.data.sumOf { it.quantity }
                    } else 0
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = 0
                )
        } else {
            MutableStateFlow(0).asStateFlow()
        }
    }
}