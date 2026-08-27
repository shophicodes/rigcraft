package com.example.rigcraft.ui.feature.catalog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        loadCategories()
        loadProducts()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            productRepository.getCategories().collect { result ->
                if (result is Resource.Success) {
                    _uiState.update { it.copy(categories = result.data) }
                }
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val currentState = _uiState.value
            productRepository.getFilteredProducts(
                categoryId = currentState.selectedCategoryId,
                subcategoryId = currentState.selectedSubcategoryId,
                minPrice = currentState.minPrice,
                maxPrice = currentState.maxPrice
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val sorted = applySortingAndSearch(result.data, currentState)
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

    private fun applySortingAndSearch(rawList: List<ProductDto>, state: CatalogUiState): List<ProductDto> {

        var filtered = rawList

        // In-memory text search filtering
        if (state.searchQuery.isNotBlank()) {
            filtered = filtered.filter {
                it.title.contains(state.searchQuery, ignoreCase = true) ||
                        it.brand.contains(state.searchQuery, ignoreCase = true)
            }
        }

        // Apply sorting
        return when (state.selectedSortOption) {
            SortOption.NEWEST -> filtered
            SortOption.PRICE_LOW_TO_HIGH -> filtered.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> filtered.sortedByDescending { it.price }
            SortOption.HIGHEST_RATED -> filtered.sortedByDescending { it.ratingAverage }
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

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
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