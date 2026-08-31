package com.example.rigcraft.ui.feature.profile

import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rigcraft.ui.feature.auth.AuthViewModel
import com.example.rigcraft.ui.feature.order.OrderViewModel
import com.example.rigcraft.data.model.OrderDto
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.example.rigcraft.R
import com.example.rigcraft.data.model.AddressDto
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onOrderClick: (String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val state by profileViewModel.uiState.collectAsStateWithLifecycle()
    val orders by orderViewModel.orders.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var currentSection by remember { mutableStateOf(ProfileSection.MENU) }

    LaunchedEffect(state.message, state.errorMessage) {
        state.message?.let { snackbarHostState.showSnackbar(it); profileViewModel.clearMessages() }
        state.errorMessage?.let { snackbarHostState.showSnackbar(it); profileViewModel.clearMessages() }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentSection) {
                            ProfileSection.MENU -> stringResource(R.string.profile_title_menu)
                            ProfileSection.PERSONAL_INFO -> stringResource(R.string.profile_title_settings)
                            ProfileSection.ADDRESSES -> stringResource(R.string.profile_title_addresses)
                            ProfileSection.ORDERS -> stringResource(R.string.profile_title_orders)
                        }
                    )
                },
                navigationIcon = {
                    if (currentSection != ProfileSection.MENU) {
                        IconButton(onClick = { currentSection = ProfileSection.MENU }) {
                            Icon(
                                painterResource(R.drawable.arrow_back_24px),
                                contentDescription = stringResource(R.string.content_desc_back_navigation)
                            )
                        }
                    }
                },
            )
        }
    ) { padding ->
        Crossfade(
            targetState = currentSection,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            label = "ProfileSectionAnimation"
        ) { section ->
            when (section) {
                ProfileSection.MENU -> ProfileMenuContent(
                    userName = state.userName,
                    userEmail = state.userEmail,
                    onNavigateToSection = { selectedSection ->
                        currentSection = selectedSection
                    },
                    onLogout = onLogout,
                    authViewModel = authViewModel
                )
                ProfileSection.PERSONAL_INFO -> PersonalInfoSection(profileViewModel, state)
                // TO DO: Implement Addresses and Orders sections and it's navigations
                ProfileSection.ADDRESSES -> AddressesSection(profileViewModel, state)
                ProfileSection.ORDERS -> OrdersSection(orders, onOrderClick)
            }
        }
    }

    // Dialogs
    if (state.showEditNameDialog) {
        EditInputDialog(
            title = stringResource(R.string.dialog_title_change_name),
            initialValue = state.userName,
            isPassword = false,
            onConfirm = { profileViewModel.updateName(it) },
            onDismiss = { profileViewModel.toggleNameDialog(false) }
        )
    }
    if (state.showEditPasswordDialog) {
        EditInputDialog(
            title = stringResource(R.string.dialog_title_new_password),
            initialValue = "",
            isPassword = true,
            onConfirm = { profileViewModel.updatePassword(it) },
            onDismiss = { profileViewModel.togglePasswordDialog(false) })
    }
    if (state.showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = { profileViewModel.toggleDeleteDialog(false) },
            title = { Text(stringResource(R.string.dialog_title_delete_account)) },
            text = { Text(stringResource(R.string.dialog_msg_delete_account_confirm)) },
            confirmButton = {
                Button(
                    onClick = { profileViewModel.deleteAccount(onLogout) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.label_delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { profileViewModel.toggleDeleteDialog(false) }) {
                    Text(stringResource(R.string.label_cancel))
                }
            }
        )
    }
    state.addressToEdit?.let { address ->
        AddressDialog(
            address = address,
            state = state.addressFormState,
            onNameChange = profileViewModel::onAddressNameChanged,
            onPhoneChange = profileViewModel::onAddressPhoneChanged,
            onStreetChange = profileViewModel::onAddressStreetChanged,
            onCityChange = profileViewModel::onAddressCityChanged,
            onZipChange = profileViewModel::onAddressZipChanged,
            onSave = profileViewModel::saveAddress,
            onDismiss = { profileViewModel.editAddress(null) }
        )
    }
}

@Composable
fun ProfileMenuContent(
    userName: String,
    userEmail: String,
    onNavigateToSection: (ProfileSection) -> Unit,
    onLogout: () -> Unit,
    authViewModel: AuthViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // User Header Summary
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_extra_large)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.account_circle_24px),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.profile_avatar_size)),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

        // Menu Rows organized in a vertical Column
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileMenuRow(
                icon = painterResource(R.drawable.settings_24px),
                title = stringResource(R.string.profile_title_settings),
                subtitle = stringResource(R.string.profile_menu_subtitle_settings),
                onClick = { onNavigateToSection(ProfileSection.PERSONAL_INFO) }
            )
            ProfileMenuRow(
                icon = painterResource(R.drawable.location_on_24px),
                title = stringResource(R.string.profile_menu_title_addresses),
                subtitle = stringResource(R.string.profile_menu_subtitle_addresses),
                onClick = { onNavigateToSection(ProfileSection.ADDRESSES) }
            )
            ProfileMenuRow(
                icon = painterResource(R.drawable.shopping_bag_24px),
                title = stringResource(R.string.profile_menu_title_orders),
                subtitle = stringResource(R.string.profile_menu_subtitle_orders),
                onClick = { onNavigateToSection(ProfileSection.ORDERS) }
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Button(
                onClick = { authViewModel.logout(onLogout) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(painterResource(R.drawable.logout_24px), null)
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text(stringResource(R.string.logout_button))
            }
        }
    }
}

@Composable
fun ProfileMenuRow(
    icon: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_extra_large),
                    vertical = dimensionResource(R.dimen.padding_medium)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(dimensionResource(R.dimen.progress_indicator_size))
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                painter = painterResource(R.drawable.chevron_right_24px),
                contentDescription = stringResource(R.string.content_desc_go_to_format, title),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(dimensionResource(R.dimen.progress_indicator_size))
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
            thickness = dimensionResource(R.dimen.divider_thickness)
        )
    }
}

// Settings section
@Composable
fun PersonalInfoSection(
    viewModel: ProfileViewModel,
    state: ProfileUiState
) {
    Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        ListItem(
            headlineContent = { Text(stringResource(R.string.display_name_label)) },
            supportingContent = { Text(state.userName) },
            trailingContent = {
                IconButton(
                    onClick = { viewModel.toggleNameDialog(true) })
                {
                    Icon(painterResource(R.drawable.edit_24px), null)
                }
            })
        HorizontalDivider()
        ListItem(
            headlineContent = { Text(stringResource(R.string.password_label)) },
            supportingContent = { Text("••••••••") },
            trailingContent = {
                IconButton(
                    onClick = { viewModel.togglePasswordDialog(true) })
                {
                    Icon(painterResource(R.drawable.edit_24px), null)
                }
            })
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        Button(
            onClick = { viewModel.toggleDeleteDialog(true) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painterResource(R.drawable.delete_24px), null)
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
            Text(stringResource(R.string.dialog_title_delete_account))
        }
    }
}

// Address section
@Composable
fun AddressesSection(viewModel: ProfileViewModel, state: ProfileUiState) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.addresses.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.msg_no_addresses_found))
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                items(state.addresses) { address ->
                    var showConfirmDeleteDialog by remember { mutableStateOf(false) }

                    if (showConfirmDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showConfirmDeleteDialog = false },
                            title = { Text(stringResource(R.string.content_desc_delete_address)) },
                            text = { Text(stringResource(R.string.dialog_msg_delete_account_confirm)) },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        viewModel.deleteAddress(address.addressId)
                                        showConfirmDeleteDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text(stringResource(R.string.label_delete))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showConfirmDeleteDialog = false }) {
                                    Text(stringResource(R.string.label_cancel))
                                }
                            }
                        )
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(address.fullName, fontWeight = FontWeight.Bold)
                                Text("${address.phoneNumber}, ${address.street}, ${address.city}, ${address.zip}")
                            }
                            IconButton(onClick = { viewModel.editAddress(address) }) {
                                Icon(
                                    painterResource(R.drawable.edit_24px),
                                    contentDescription = stringResource(R.string.content_desc_edit_address)
                                )
                            }
                            IconButton(onClick = { showConfirmDeleteDialog = true }) {
                                Icon(
                                    painterResource(R.drawable.delete_24px),
                                    contentDescription = stringResource(R.string.content_desc_delete_address),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { viewModel.editAddress(AddressDto()) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Icon(
                painterResource(R.drawable.add_24px),
                contentDescription = stringResource(R.string.content_desc_add_address)
            )
        }
    }
}

// Helper dialog composables
@Composable
fun EditInputDialog(
    title: String,
    initialValue: String,
    isPassword: Boolean = false,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(text) }) {
                Text(stringResource(R.string.label_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.label_cancel))
            }
        }
    )
}

@Composable
fun OrdersSection(orders: List<OrderDto>, onOrderClick: (String) -> Unit) {
    if (orders.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.msg_no_orders_found))
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(orders) { order ->
                OrderHistoryCard(order, onClick = { onOrderClick(order.orderId) })
            }
        }
    }
}

@Composable
fun OrderHistoryCard(order: OrderDto, onClick: () -> Unit) {
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy. HH:mm", Locale.getDefault()) }
    val dateString = order.createdAt?.toDate()?.let { dateFormat.format(it) } ?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.order_id_format, order.orderId.takeLast(8)),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = order.orderStatus,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_small)))
            Text(text = stringResource(R.string.order_date_format, dateString), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun AddressDialog(
    address: AddressDto,
    state: AddressFormState,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onZipChange: (String) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit
) {
    val (nameFR, phoneFR, streetFR, cityFR, zipFR) = remember { FocusRequester.createRefs() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (address.addressId.isEmpty()) stringResource(R.string.dialog_title_add_address)
                else stringResource(R.string.dialog_title_edit_address)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = onNameChange,
                    label = { Text(stringResource(R.string.label_full_name)) },
                    isError = state.nameError != null,
                    supportingText = state.nameError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFR),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { phoneFR.requestFocus() })
                )
                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = onPhoneChange,
                    label = { Text(stringResource(R.string.label_phone_number)) },
                    isError = state.phoneError != null,
                    supportingText = state.phoneError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(phoneFR),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { streetFR.requestFocus() })
                )
                OutlinedTextField(
                    value = state.street,
                    onValueChange = onStreetChange,
                    label = { Text(stringResource(R.string.label_street)) },
                    isError = state.streetError != null,
                    supportingText = state.streetError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(streetFR),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { cityFR.requestFocus() })
                )
                OutlinedTextField(
                    value = state.city,
                    onValueChange = onCityChange,
                    label = { Text(stringResource(R.string.label_city)) },
                    isError = state.cityError != null,
                    supportingText = state.cityError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(cityFR),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { zipFR.requestFocus() })
                )
                OutlinedTextField(
                    value = state.zip,
                    onValueChange = onZipChange,
                    label = { Text(stringResource(R.string.label_zip_code)) },
                    isError = state.zipError != null,
                    supportingText = state.zipError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(zipFR),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onSave() })
                )
            }
        },
        confirmButton = {
            Button(onClick = onSave) {
                Text(stringResource(R.string.label_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.label_cancel))
            }
        }
    )
}

