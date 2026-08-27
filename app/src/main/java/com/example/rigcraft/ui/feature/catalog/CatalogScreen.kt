package com.example.rigcraft.ui.feature.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rigcraft.ui.components.ProductCard
import com.example.rigcraft.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onProductClick: (String) -> Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Browse Catalog") },
                actions = {
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(
                            painter = painterResource(R.drawable.filter_list_24px),
                            contentDescription = "Sort Options"
                        )
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Newest") },
                            onClick = {
                                viewModel.updateSortOption(SortOption.NEWEST)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Price: Low to High") },
                            onClick = {
                                viewModel.updateSortOption(SortOption.PRICE_LOW_TO_HIGH)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Price: High to Low") },
                            onClick = {
                                viewModel.updateSortOption(SortOption.PRICE_HIGH_TO_LOW)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Highest Rated") },
                            onClick = {
                                viewModel.updateSortOption(SortOption.HIGHEST_RATED)
                                showSortMenu = false
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // --- 1. SEARCH BAR ---
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search GPUs, CPUs, motherboards...") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search_24px),
                        contentDescription = null
                    )
                },
                singleLine = true
            )

            // --- 2. DYNAMIC CATEGORY FILTER CHIPS ---
            val topLevelCategories = remember(state.categories) {
                state.categories.filter { it.parentCategory == null }
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = state.selectedCategoryId == null,
                        onClick = { viewModel.selectCategory(null) },
                        label = { Text("All") }
                    )
                }
                items(topLevelCategories) { category ->
                    FilterChip(
                        selected = state.selectedCategoryId == category.categoryId,
                        onClick = { viewModel.selectCategory(category.categoryId) },
                        label = { Text(category.name) }
                    )
                }
            }

            // --- 3. SUBCATEGORY CHIPS (IF A CATEGORY IS SELECTED) ---
            val subcategories = remember(state.categories, state.selectedCategoryId) {
                if (state.selectedCategoryId != null) {
                    state.categories.filter { it.parentCategory == state.selectedCategoryId }
                } else {
                    emptyList()
                }
            }

            if (subcategories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    item {
                        FilterChip(
                            selected = state.selectedSubcategoryId == null,
                            onClick = { viewModel.selectSubcategory(null) },
                            label = { Text("All Subcategories") }
                        )
                    }
                    // Pass the filtered List of CategoryDto objects to items()
                    items(subcategories) { subcat ->
                        FilterChip(
                            selected = state.selectedSubcategoryId == subcat.categoryId,
                            onClick = { viewModel.selectSubcategory(subcat.categoryId) },
                            label = { Text(subcat.name) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- 4. PRODUCT GRID ---
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No products found.", fontWeight = FontWeight.SemiBold)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.products) { product ->
                        ProductCard(
                            product = product,
                            onProductClick = onProductClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}