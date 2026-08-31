package com.example.rigcraft.ui.feature.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.rigcraft.R
import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.data.model.CartItemDto
import com.example.rigcraft.ui.feature.cart.SummaryRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAddAddress: () -> Unit,
    onOrderPlaced: (String) -> Unit,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val successMsg = stringResource(R.string.msg_order_success)

    LaunchedEffect(state.orderPlacedSuccessfully) {
        if (state.orderPlacedSuccessfully) {
            onOrderPlaced(successMsg)
            onNavigateToHome()
            viewModel.resetOrderState()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.selectedAddress == null) stringResource(R.string.nav_address_select)
                        else stringResource(R.string.nav_confirm_order)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(painterResource(R.drawable.arrow_back_24px), contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (state.addresses.isEmpty()) {
                    NoAddressPrompt(onNavigateToAddAddress)
                } else if (state.selectedAddress == null) {
                    AddressSelectionContent(
                        addresses = state.addresses,
                        onAddressSelected = viewModel::onAddressSelected
                    )
                } else {
                    OrderConfirmationContent(
                        address = state.selectedAddress!!,
                        cartItems = state.cartItems,
                        totalPrice = state.totalPrice,
                        onPlaceOrder = viewModel::placeOrder,
                        onEditAddress = { viewModel.onAddressSelected(null) }
                    )
                }
            }
        }
    }
}

@Composable
fun NoAddressPrompt(onNavigateToAddAddress: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.location_on_24px),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.msg_no_addresses_found),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToAddAddress) {
            Text(stringResource(R.string.content_desc_add_address))
        }
    }
}

@Composable
fun AddressSelectionContent(
    addresses: List<AddressDto>,
    onAddressSelected: (AddressDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(addresses) { address ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onAddressSelected(address) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = address.fullName, fontWeight = FontWeight.Bold)
                        Text(text = "${address.street}, ${address.city}, ${address.zip}")
                        Text(text = address.phoneNumber)
                    }
                    Icon(painterResource(R.drawable.chevron_right_24px), contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun OrderConfirmationContent(
    address: AddressDto,
    cartItems: List<CartItemDto>,
    totalPrice: Double,
    onPlaceOrder: () -> Unit,
    onEditAddress: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.profile_title_addresses),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = address.fullName, fontWeight = FontWeight.Bold)
                            Text(text = "${address.street}, ${address.city}, ${address.zip}")
                            Text(text = address.phoneNumber)
                        }
                        IconButton(onClick = onEditAddress) {
                            Icon(painterResource(R.drawable.edit_24px), contentDescription = null)
                        }
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.nav_cart),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(cartItems) { item ->
                OrderItemCard(item)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    SummaryRow(
                        label = stringResource(R.string.label_total),
                        amount = totalPrice,
                        isBold = true
                    )
                    Text(
                        text = stringResource(R.string.label_cash_on_delivery),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Surface(
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Button(
                onClick = onPlaceOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.nav_confirm_order))
            }
        }
    }
}

@Composable
fun OrderItemCard(item: CartItemDto) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)).background(Color.White),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, maxLines = 1, fontWeight = FontWeight.Medium)
            Text(
                text = stringResource(R.string.cart_item_price_quantity_format, item.price, stringResource(R.string.currency_rsd), item.quantity),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}