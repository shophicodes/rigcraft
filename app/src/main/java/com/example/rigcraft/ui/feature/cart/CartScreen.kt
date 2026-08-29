package com.example.rigcraft.ui.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.rigcraft.data.model.CartItemDto
import com.example.rigcraft.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onCheckoutClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_shopping_cart, state.cartItems.sumOf { it.quantity })) }
            )
        },
        bottomBar = {
            if (state.cartItems.isNotEmpty()) {
                CartSummaryBottomBar(
                    subtotal = state.subtotal,
                    shipping = state.shippingFee,
                    total = state.total,
                    onCheckoutClick = onCheckoutClick
                )
            }
        }
    ) { paddingValues ->
        if(state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else if(state.cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(R.drawable.shopping_cart_24px),
                        contentDescription = null,
                        modifier = Modifier.size(dimensionResource(R.dimen.category_item_size)),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                    Text(stringResource(R.string.msg_empty_cart), style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_spaced_by))
            ) {
                items(state.cartItems, key = { it.itemId }) { item ->
                    CartItemCard(
                        item = item,
                        onQuantityChange = { n ->
                            viewModel.updateQuantity(item.itemId, item.quantity, n)
                        },
                        onRemove = { viewModel.removeItem(item.itemId) }
                    )
                }
            }
        }
    }
}

@Composable
fun CartSummaryBottomBar(
    subtotal: Double,
    shipping: Double,
    total: Double,
    onCheckoutClick: () -> Unit
) {
    Surface(
        shadowElevation = dimensionResource(R.dimen.padding_spaced_by),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            SummaryRow(label = stringResource(R.string.label_subtotal), amount = subtotal)
            SummaryRow(
                label = stringResource(R.string.label_shipping),
                amount = shipping,
                overrideText = if (shipping == 0.0) stringResource(R.string.label_free) else null
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small)))
            SummaryRow(label = stringResource(R.string.label_total), amount = total, isBold = true)

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_spaced_by)))

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.bottom_bar_item_height)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.product_card_corner_radius))
            ) {
                Text(stringResource(R.string.label_proceed_to_checkout))
            }
        }
    }
}

@Composable
fun SummaryRow(
    label: String,
    amount: Double,
    isBold: Boolean = false,
    overrideText: String? = null
) {
    val density = LocalDensity.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.badge_padding_vertical)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = with(density) {
                dimensionResource(if (isBold) R.dimen.text_size_medium else R.dimen.text_size_small).toSp()
            }
        )
        Text(
            text = overrideText ?: stringResource(R.string.price_format, amount, stringResource(R.string.currency_rsd)),
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = with(density) {
                dimensionResource(if (isBold) R.dimen.text_size_medium else R.dimen.text_size_small).toSp()
            },
            color = if (isBold) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}

@Composable
fun CartItemCard(
    item: CartItemDto,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_spaced_by))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.cart_item_image_size))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                    .background(Color.White)
                    .padding(dimensionResource(R.dimen.padding_extra_small))
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_spaced_by)))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))

                if(item.quantity > 1) {
                    Text(
                        text = stringResource(
                            R.string.cart_item_price_quantity_format,
                            item.price,
                            stringResource(R.string.currency_rsd),
                            item.quantity
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                else {
                    Text(
                        text = stringResource(R.string.price_format, item.price, stringResource(R.string.currency_rsd)),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                // Quantity Modifier
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.small_corner_radius)))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    IconButton(
                        onClick = { onQuantityChange(-1) },
                        enabled = item.quantity > 1,
                        modifier = Modifier.size(dimensionResource(R.dimen.small_button_size))
                    ) {
                        Icon(
                            painterResource(R.drawable.remove_24px),
                            contentDescription = stringResource(R.string.label_decrease),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small)),
                            tint = if (item.quantity > 1) LocalContentColor.current else Color.Gray.copy(alpha = 0.5f)
                        )
                    }
                    Text(
                        text = "${item.quantity}",
                        fontWeight = FontWeight.Bold,
                        fontSize = with(LocalDensity.current) { dimensionResource(R.dimen.text_size_small).toSp() },
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
                    )
                    IconButton(
                        onClick = { onQuantityChange(1) },
                        enabled = item.quantity < 50,
                        modifier = Modifier.size(dimensionResource(R.dimen.small_button_size))
                    ) {
                        Icon(
                            painterResource(R.drawable.add_24px),
                            contentDescription = stringResource(R.string.label_increase),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_small)),
                            tint = if (item.quantity < 50) LocalContentColor.current else Color.Gray.copy(alpha = 0.5f)
                        )
                    }
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(R.drawable.delete_24px),
                    contentDescription = stringResource(R.string.content_desc_remove_item),
                    tint = Color.Red.copy(alpha = 0.7f)
                )
            }
        }
    }
}