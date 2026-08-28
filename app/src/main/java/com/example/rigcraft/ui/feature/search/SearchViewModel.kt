package com.example.rigcraft.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        performSearch(query)
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.update { it.copy(products = emptyList(), isLoading = false, errorMessage = null) }
            return
        }

        searchJob = viewModelScope.launch {
            delay(300.milliseconds)
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Using getFilteredProducts with null filters to get all, then filtering in memory
            // to match CatalogViewModel's search behavior.
            productRepository.getFilteredProducts(
                categoryId = null,
                subcategoryId = null,
                minPrice = null,
                maxPrice = null
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val filtered = result.data.filter {
                            it.title.contains(query, ignoreCase = true) ||
                                    it.brand.contains(query, ignoreCase = true)
                        }
                        _uiState.update { it.copy(isLoading = false, products = filtered) }
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    else -> {}
                }
            }
        }
    }
}
