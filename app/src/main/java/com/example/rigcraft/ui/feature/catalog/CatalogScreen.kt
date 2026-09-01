package com.example.rigcraft.ui.feature.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
                title = { Text(stringResource(R.string.title_browse_catalog)) },
                actions = {
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(
                            painter = painterResource(R.drawable.filter_list_24px),
                            contentDescription = stringResource(R.string.content_desc_sort_options)
                        )
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sort_newest)) },
                            onClick = {
                                viewModel.updateSortOption(SortOption.NEWEST)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sort_price_low_high)) },
                            onClick = {
                                viewModel.updateSortOption(SortOption.PRICE_LOW_TO_HIGH)
                                showSortMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sort_price_high_low)) },
                            onClick = {
                                viewModel.updateSortOption(SortOption.PRICE_HIGH_TO_LOW)
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
            // Dynamic Category Filter Chips
            val topLevelCategories = remember(state.categories) {
                state.categories.filter { it.parentCategory == null }
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                item {
                    FilterChip(
                        selected = state.selectedCategoryId == null,
                        onClick = { viewModel.selectCategory(null) },
                        label = { Text(stringResource(R.string.label_filter_all)) }
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

            // Subcategory items (shown if category is selected)
            val subcategories = remember(state.categories, state.selectedCategoryId) {
                if (state.selectedCategoryId != null) {
                    state.categories.filter { it.parentCategory == state.selectedCategoryId }
                } else {
                    emptyList()
                }
            }

            if (subcategories.isNotEmpty()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_extra_small))
                ) {
                    item {
                        FilterChip(
                            selected = state.selectedSubcategoryId == null,
                            onClick = { viewModel.selectSubcategory(null) },
                            label = { Text(stringResource(R.string.label_filter_all_subcategories)) }
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

            // Price Filter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.minPrice?.toString() ?: "",
                    onValueChange = {
                        val min = it.toDoubleOrNull()
                        viewModel.updatePriceFilter(min, state.maxPrice)
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.label_min_price)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = state.maxPrice?.toString() ?: "",
                    onValueChange = {
                        val max = it.toDoubleOrNull()
                        viewModel.updatePriceFilter(state.minPrice, max)
                    },
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.label_max_price)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            // Product grid
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = state.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
            } else if (state.products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.msg_no_products_found), fontWeight = FontWeight.SemiBold)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_spaced_by)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_spaced_by)),
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