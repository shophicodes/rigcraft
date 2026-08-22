package com.example.rigcraft.ui.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.rigcraft.R
import com.example.rigcraft.ui.components.AuthTextField
import com.example.rigcraft.ui.components.LoadingButton
import com.example.rigcraft.ui.theme.RigCraftTheme
import com.example.rigcraft.util.Resource

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    val authState by viewModel.authState.collectAsState()
    val uiState by viewModel.loginUiState.collectAsState()
    val rememberMe = viewModel.rememberMe

    LaunchedEffect(authState) {
        if (authState is Resource.Success) {
            viewModel.resetAuthState()
            onNavigateToHome()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        rememberMe = rememberMe,
        onEmailChanged = viewModel::onLoginEmailChanged,
        onPasswordChanged = viewModel::onLoginPasswordChanged,
        onRememberMeChanged = viewModel::onRememberMeChanged,
        onLogin = viewModel::login,
        onNavigateToRegister = onNavigateToRegister,
        authState = authState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreenContent(
    uiState: LoginUiState,
    rememberMe: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRememberMeChanged: (Boolean) -> Unit,
    onLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    authState: Resource<Boolean>
) {
    val focusManager = LocalFocusManager.current
    val isLoading = authState is Resource.Loading

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier.size(dimensionResource(R.dimen.logo_size))
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.login_title),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                )
                Text(
                    text = stringResource(R.string.login_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
                )

                AuthTextField(
                    value = uiState.email,
                    onValueChange = onEmailChanged,
                    label = stringResource(R.string.email_label),
                    isError = uiState.emailError != null,
                    errorMessage = uiState.emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                AuthTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChanged,
                    label = stringResource(R.string.password_label),
                    isError = uiState.passwordError != null,
                    errorMessage = uiState.passwordError,
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onLogin()
                        }
                    )
                )

                if (authState is Resource.Error) {
                    Text(
                        text = authState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small), start = dimensionResource(R.dimen.padding_small))
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(R.dimen.padding_small))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = onRememberMeChanged,
                            enabled = !isLoading
                        )
                        Text(
                            text = stringResource(R.string.remember_me),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LoadingButton(
                    text = stringResource(R.string.login_button),
                    onClick = onLogin,
                    isLoading = isLoading
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = stringResource(R.string.no_account))
                    TextButton(
                        onClick = onNavigateToRegister,
                        enabled = !isLoading
                    ) {
                        Text(text = stringResource(R.string.sign_up))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RigCraftTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            rememberMe = false,
            onEmailChanged = {},
            onPasswordChanged = {},
            onRememberMeChanged = {},
            onLogin = {},
            onNavigateToRegister = {},
            authState = Resource.Idle
        )
    }
}
