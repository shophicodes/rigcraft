package com.example.rigcraft.ui.feature.catalog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val initialCategoryId: String? = savedStateHandle["categoryId"]

    private val _uiState = MutableStateFlow(CatalogUiState(selectedCategoryId = initialCategoryId))
    val uiState = _uiState.asStateFlow()

    private var productLoadJob: Job? = null

    init {
        loadCategories()
        loadProducts()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            productRepository.getCategories().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update { it.copy(categories = result.data, errorMessage = null) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = result.message) }
                    }
                    else -> {}
                }
            }
        }
    }

    fun loadProducts() {
        productLoadJob?.cancel()
        productLoadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val currentState = _uiState.value
            val resultFlow = productRepository.getFilteredProducts(
                categoryId = currentState.selectedCategoryId,
                subcategoryId = currentState.selectedSubcategoryId,
                minPrice = currentState.minPrice,
                maxPrice = currentState.maxPrice
            )

            resultFlow.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val sorted = applySorting(result.data, currentState)
                        _uiState.update { it.copy(isLoading = false, products = sorted) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun applySorting(rawList: List<ProductDto>, state: CatalogUiState): List<ProductDto> {
        // Apply sorting
        return when (state.selectedSortOption) {
            SortOption.NEWEST -> rawList.sortedByDescending { it.createdAt }
            SortOption.PRICE_LOW_TO_HIGH -> rawList.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> rawList.sortedByDescending { it.price }
        }
    }

    fun selectCategory(categoryId: String?) {
        _uiState.update { it.copy(selectedCategoryId = categoryId, selectedSubcategoryId = null) }
        loadProducts()
    }

    fun selectSubcategory(subcategoryId: String?) {
        _uiState.update { it.copy(selectedSubcategoryId = subcategoryId) }
        loadProducts()
    }

    fun updatePriceFilter(min: Double?, max: Double?) {
        _uiState.update { it.copy(minPrice = min, maxPrice = max) }
        loadProducts()
    }

    fun updateSortOption(sortOption: SortOption) {
        _uiState.update { it.copy(selectedSortOption = sortOption) }
        loadProducts()
    }
}