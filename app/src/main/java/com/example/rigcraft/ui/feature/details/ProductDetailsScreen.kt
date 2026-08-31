package com.example.rigcraft.ui.feature.details

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.rigcraft.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.rigcraft.data.model.ProductDto
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsScreen(
    onBackClick: () -> Unit,
    onNavigateToCart: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val goToCartLabel = stringResource(R.string.label_go_to_cart)

    // Display feedback message when product is added to cart
    LaunchedEffect(state.userMessage, state.cartErrorMessage, state.errorMessage) {
        state.userMessage?.let {
            val result = snackbarHostState.showSnackbar(
                message = it,
                actionLabel = goToCartLabel,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                onNavigateToCart()
            }
            viewModel.clearUserMessage()
        }
        state.cartErrorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearUserMessage()
        }
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearUserMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back_24px),
                            contentDescription = stringResource(R.string.label_back)
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (state.product != null) {
                AddToCartBottomBar(
                    product = state.product!!,
                    quantity = state.selectedQuantity,
                    onIncrement = viewModel::incrementQuantity,
                    onDecrement = viewModel::decrementQuantity,
                    onAddToCartClick = viewModel::addToCart,
                    isAddingToCart = state.isAddingToCart
                )
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.errorMessage != null) {
            ProductErrorState(
                message = state.errorMessage!!,
                actionLabel = stringResource(R.string.label_retry),
                onAction = viewModel::retry,
                modifier = Modifier.padding(paddingValues)
            )
        } else if (state.product == null) {
            ProductErrorState(
                message = stringResource(R.string.error_product_unavailable),
                actionLabel = stringResource(R.string.label_back),
                onAction = onBackClick,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            val product = state.product!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Image Carousel
                val images = product.images.ifEmpty { listOf("") }
                val pagerState = rememberPagerState(pageCount = { images.size })

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.product_carousel_height))
                        .background(Color.White)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        AsyncImage(
                            model = images[page],
                            contentDescription = product.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(R.dimen.padding_medium))
                        )
                    }

                    // Pager Page Indicator
                    if (images.size > 1) {
                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = dimensionResource(R.dimen.padding_small)),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color = if (pagerState.currentPage == iteration) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    Color.LightGray
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(dimensionResource(R.dimen.badge_padding_vertical))
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(dimensionResource(R.dimen.indicator_dot_size))
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))) {
                    // Title & Brand
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                    // Rating & Stock Status Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.star_24px),
                            contentDescription = stringResource(R.string.label_rating),
                            tint = Color(0xFFFFB800),
                            modifier = Modifier.size(dimensionResource(R.dimen.rating_star_size))
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_small)))
                        Text(
                            text = stringResource(R.string.product_rating_format, product.ratingAverage, product.reviewCount),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    if (product.inStock) {
                                        stringResource(R.string.product_in_stock_format, product.stockQuantity)
                                    } else {
                                        stringResource(R.string.product_out_of_stock)
                                    }
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (product.inStock) Color(0xFFE6F4EA) else Color(0xFFFCE8E6),
                                labelColor = if (product.inStock) Color(0xFF137333) else Color(0xFFC5221F)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    // Price Tag
                    val finalPrice = if (product.discountPercent > 0) {
                        product.price * (1 - product.discountPercent / 100.0)
                    } else product.price

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(R.dimen.padding_medium),
                                vertical = dimensionResource(R.dimen.padding_small)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.price_format, finalPrice, stringResource(R.string.currency_rsd)),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            if (product.discountPercent > 0) {
                                Text(
                                    text = stringResource(R.string.price_format, product.price, stringResource(R.string.currency_rsd)),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.toggleWishlist(product) }
                        ) {
                            val isWishlisted = viewModel.isWishlisted.collectAsState().value
                            Icon(
                                painter = painterResource(R.drawable.favorite_24px),
                                contentDescription = if (isWishlisted)
                                    stringResource(R.string.content_desc_remove_from_wishlist)
                                else stringResource(R.string.content_desc_add_to_wishlist),
                                tint = if (isWishlisted)
                                    Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(dimensionResource(R.dimen.wishlist_heart_icon_size))
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    // Specifications List
                    Text(
                        text = stringResource(R.string.title_technical_specifications),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_spaced_by))) {
                            product.specifications.forEach { (key, value) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = dimensionResource(R.dimen.padding_extra_small)),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = key,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = value,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.badge_padding_vertical)),
                                    color = Color.LightGray.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
                }
            }
        }
    }
}

@Composable
fun ProductErrorState(
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
        Button(onClick = onAction) {
            Text(text = actionLabel)
        }
    }
}

// Sticky Bottom Bar Composable
@Composable
fun AddToCartBottomBar(
    product: ProductDto,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onAddToCartClick: () -> Unit,
    isAddingToCart: Boolean = false
) {
    Surface(
        shadowElevation = dimensionResource(R.dimen.padding_small),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Quantity Selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .height(dimensionResource(R.dimen.bottom_bar_item_height))
            ) {
                IconButton(onClick = onDecrement, enabled = !isAddingToCart, modifier = Modifier.size(dimensionResource(R.dimen.quantity_selector_button_size))) {
                    Icon(painterResource(R.drawable.remove_24px), contentDescription = stringResource(R.string.label_decrease))
                }
                Text(
                    text = "$quantity",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
                IconButton(onClick = onIncrement, enabled = !isAddingToCart, modifier = Modifier.size(dimensionResource(R.dimen.quantity_selector_button_size))) {
                    Icon(painterResource(R.drawable.add_24px), contentDescription = stringResource(R.string.label_increase))
                }
            }

            // Add To Cart Button
            Button(
                onClick = onAddToCartClick,
                enabled = product.inStock && !isAddingToCart,
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_spaced_by)),
                modifier = Modifier.height(dimensionResource(R.dimen.bottom_bar_item_height))
            ) {
                if (isAddingToCart) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small)),
                        strokeWidth = dimensionResource(R.dimen.badge_padding_vertical),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(painterResource(R.drawable.shopping_cart_24px), contentDescription = null)
                }
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text(stringResource(R.string.label_add_to_cart))
            }
        }
    }
}
