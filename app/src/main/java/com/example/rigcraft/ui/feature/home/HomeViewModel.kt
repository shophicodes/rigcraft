package com.example.rigcraft.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.domain.repository.SeederRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val seederRepository: SeederRepository,
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHomeData()
        seedMockData()
    }

    fun retry() {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            combine(
                repository.getCategories(),
                repository.getRecentProducts(),
                repository.getProductsOnSale()
            ) { categoriesRes, recentRes, saleRes ->
                var error: String? = null
                var categories = emptyList<CategoryDto>()
                var recent = emptyList<ProductDto>()
                var sale = emptyList<ProductDto>()

                when(categoriesRes) {
                    is Resource.Success -> categories = categoriesRes.data
                    is Resource.Error -> error = categoriesRes.message
                    else -> {}
                }

                when (recentRes) {
                    is Resource.Success -> recent = recentRes.data
                    is Resource.Error -> error = recentRes.message
                    else -> {}
                }

                when (saleRes) {
                    is Resource.Success -> sale = saleRes.data
                    is Resource.Error -> error = saleRes.message
                    else -> {}
                }

                HomeUiState(
                    isLoading = false,
                    categories = categories,
                    recentProducts = recent,
                    saleProducts = sale,
                    errorMessage = error
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    private fun seedMockData() {
        viewModelScope.launch {
            seederRepository.seedData().collect { result ->
                when (result) {
                    is Resource.Success -> Log.d("FirestoreSeeder", result.data)
                    is Resource.Error -> Log.e("FirestoreSeeder", result.message)
                    else -> {}
                }
            }
        }
    }
}
