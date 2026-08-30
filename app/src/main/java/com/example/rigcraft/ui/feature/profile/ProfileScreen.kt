package com.example.rigcraft.ui.feature.profile

import android.app.AlertDialog
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rigcraft.ui.feature.auth.AuthViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.example.rigcraft.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by profileViewModel.uiState.collectAsStateWithLifecycle()
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
                            ProfileSection.MENU -> "Moj nalog"
                            ProfileSection.PERSONAL_INFO -> "Podešavanja naloga"
                            ProfileSection.ADDRESSES -> "Dostavne Adrese"
                            ProfileSection.ORDERS -> "Istorija porudžbina"
                        }
                    )
                },
                navigationIcon = {
                    if (currentSection != ProfileSection.MENU) {
                        IconButton(onClick = { currentSection = ProfileSection.MENU }) {
                            Icon(painterResource(R.drawable.arrow_back_24px), contentDescription = "Vrati se nazad")
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
                ProfileSection.ADDRESSES -> {}
                ProfileSection.ORDERS -> {}
            }
        }
    }

    // Dialogs
    if (state.showEditNameDialog) {
        EditInputDialog(
            title = "Promeni ime",
            initialValue = state.userName,
            isPassword = false,
            onConfirm = { profileViewModel.updateName(it) },
            onDismiss = { profileViewModel.toggleNameDialog(false) }
        )
    }
    if (state.showEditPasswordDialog) {
        EditInputDialog(
            title = "Nova lozinka",
            initialValue = "",
            isPassword = true,
            onConfirm = { profileViewModel.updatePassword(it) },
            onDismiss = { profileViewModel.togglePasswordDialog(false) })
    }
    if (state.showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = { profileViewModel.toggleDeleteDialog(false) },
            title = { Text("Obriši nalog") },
            text = { Text("Da li ste sigurni? Ova radnja se ne može poništiti.") },
            confirmButton = { Button(onClick = { profileViewModel.deleteAccount(onLogout) }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) { Text("Obriši") } },
            dismissButton = { TextButton(onClick = { profileViewModel.toggleDeleteDialog(false) }) { Text("Otkaži") } }
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
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.account_circle_24px),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
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
        Spacer(modifier = Modifier.height(8.dp))

        // Menu Rows organized in a vertical Column
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileMenuRow(
                icon = painterResource(R.drawable.settings_24px),
                title = "Podešavanja naloga",
                subtitle = "Upravljaj sa nalogom",
                onClick = { onNavigateToSection(ProfileSection.PERSONAL_INFO) }
            )
            ProfileMenuRow(
                icon = painterResource(R.drawable.location_on_24px),
                title = "Upravljaj sa doatavnim adresama",
                subtitle = "Dodaj, ažuriraj ili obriši dostavne adrese",
                onClick = { onNavigateToSection(ProfileSection.ADDRESSES) }
            )
            ProfileMenuRow(
                icon = painterResource(R.drawable.shopping_bag_24px),
                title = "Pogledaj porudžbine",
                subtitle = "Proveri istoriju porudžbina",
                onClick = { onNavigateToSection(ProfileSection.ORDERS) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { authViewModel.logout(onLogout) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(painterResource(R.drawable.logout_24px), null)
                Text("Odjavi se")
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
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
                contentDescription = "Idi na $title",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
            thickness = 1.dp
        )
    }
}

// Settings sections
@Composable
fun PersonalInfoSection(
    viewModel: ProfileViewModel,
    state: ProfileUiState
) {
    Column(modifier = Modifier.padding(16.dp)) {
        ListItem(
            headlineContent = { Text("Ime") },
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
            headlineContent = { Text("Lozinka") },
            supportingContent = { Text("••••••••") },
            trailingContent = {
                IconButton(
                    onClick = { viewModel.togglePasswordDialog(true) })
                {
                    Icon(painterResource(R.drawable.edit_24px), null)
                }
            })
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.toggleDeleteDialog(true) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painterResource(R.drawable.delete_24px), null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Obriši nalog")
        }
    }
}

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
        confirmButton = { Button(onClick = { onConfirm(text) }) { Text("Sačuvaj") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Otkaži") } }
    )
}