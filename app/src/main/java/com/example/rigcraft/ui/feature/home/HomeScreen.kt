package com.example.rigcraft.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.rigcraft.R
import com.example.rigcraft.data.model.CategoryDto
import com.example.rigcraft.ui.components.ProductCard

@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit = {},
    onProductClick: (String) -> Unit = {},
    onSeeAllClick: (String) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if(state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = dimensionResource(R.dimen.padding_medium))
        ) {
            // Categories Carousel
            Text(
                text = stringResource(R.string.home_categories),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_spaced_by)))

            LazyRow(
                contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                items(state.categories) { category ->
                    CategoryCarouselItem(category = category, onClick = onCategoryClick)
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            // Recently added products
            SectionHeader(
                title = stringResource(R.string.home_recently_added),
                onSeeAllClick = { onSeeAllClick("recent") }
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_spaced_by)))

            LazyRow(
                contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_spaced_by))
            ) {
                items(state.recentProducts.take(6)) { product ->
                    ProductCard(product = product, onProductClick = onProductClick)
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            // Products on sale
            SectionHeader(
                title = stringResource(R.string.home_deals_and_sales),
                onSeeAllClick = { onSeeAllClick("sales") }
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_spaced_by)))

            LazyRow(
                contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_spaced_by))
            ) {
                items(state.saleProducts.take(6)) { product ->
                    ProductCard(product = product, onProductClick = onProductClick)
                }
            }
        }
    }
}

@Composable
fun CategoryCarouselItem(
    category: CategoryDto,
    onClick: (String) -> Unit
) {
    if(category.parentCategory == null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onClick(category.categoryId) }
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.category_item_size))
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(dimensionResource(R.dimen.padding_small)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = category.imageUrl,
                    contentDescription = category.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
            Text(
                text = category.name,
                fontSize = with(LocalDensity.current) { dimensionResource(R.dimen.category_text_size).toSp() },
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = onSeeAllClick) {
            Text(stringResource(R.string.label_see_all), color = MaterialTheme.colorScheme.primary)
        }
    }
}
