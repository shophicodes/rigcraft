package com.example.rigcraft.ui.feature.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rigcraft.R
import com.example.rigcraft.data.model.OrderDto
import com.example.rigcraft.util.Resource
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    onBackClick: () -> Unit,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nav_order_info)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(R.drawable.arrow_back_24px), contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    OrderDetailContent(order = state.data)
                }
                else -> {}
            }
        }
    }
}

@Composable
fun OrderDetailContent(order: OrderDto) {
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault()) }
    val dateString = order.createdAt?.toDate()?.let { dateFormat.format(it) } ?: ""

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        // Basic Info
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                    Text(
                        text = stringResource(R.string.order_id_format, order.orderId),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
                    Text(text = stringResource(R.string.order_date_format, dateString))
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
                    Text(
                        text = stringResource(R.string.order_status_format, order.orderStatus),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Shipping Address
        item {
            Text(
                text = stringResource(R.string.order_address_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            order.shippingAddress?.let { address ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                        Text(text = address.fullName, fontWeight = FontWeight.Bold)
                        Text(text = "${address.street}, ${address.city}, ${address.zip}")
                        Text(text = address.phoneNumber)
                    }
                }
            }
        }

        // Items
        item {
            Text(
                text = stringResource(R.string.nav_cart),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(order.items) { item ->
            val itemTotalPrice = item.price * item.quantity
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.title, fontWeight = FontWeight.Medium, maxLines = 1)
                        Text(
                            text = "${item.quantity} x ${stringResource(R.string.price_format, item.price, stringResource(R.string.currency_rsd))} = ${stringResource(R.string.price_format, itemTotalPrice, stringResource(R.string.currency_rsd))}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Summary
        item {
            HorizontalDivider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.label_total),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.price_format, order.totalAmount, stringResource(R.string.currency_rsd)),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = stringResource(R.string.label_cash_on_delivery),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
