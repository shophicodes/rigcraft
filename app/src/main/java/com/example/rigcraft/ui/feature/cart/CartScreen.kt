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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                title = { Text("Shopping Cart (${state.cartItems.sumOf { it.quantity }})") }
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
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Your cart is empty", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
        shadowElevation = 12.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SummaryRow(label = "Subtotal", amount = subtotal)
            SummaryRow(
                label = "Shipping",
                amount = shipping,
                overrideText = if (shipping == 0.0) "FREE" else null
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryRow(label = "Total", amount = total, isBold = true)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Proceed to Checkout")
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = if (isBold) 16.sp else 14.sp
        )
        Text(
            text = overrideText ?: "${"%.2f".format(amount)} RSD",
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = if (isBold) 16.sp else 14.sp,
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
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))

                if(item.quantity > 1) {
                    Text(
                        text = "${"%.2f".format(item.price)} RSD x${item.quantity}",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                else {
                    Text(
                        text = "${"%.2f".format(item.price)} RSD",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Quantity Modifier
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    IconButton(
                        onClick = { onQuantityChange(-1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(painterResource(R.drawable.remove_24px), contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                    }
                    Text(
                        text = "${item.quantity}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = { onQuantityChange(1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(painterResource(R.drawable.add_24px), contentDescription = "Increase", modifier = Modifier.size(16.dp))
                    }
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(R.drawable.delete_24px),
                    contentDescription = "Remove Item",
                    tint = Color.Red.copy(alpha = 0.7f)
                )
            }
        }
    }
}