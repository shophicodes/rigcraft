package com.example.rigcraft.ui.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.local.UserPreferences
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.util.FormValidation
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences
): ViewModel() {
    private val _authState = MutableStateFlow<Resource<Boolean>>(Resource.Idle)
    val authState = _authState.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState = _registerUiState.asStateFlow()

    var rememberMe by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            rememberMe = userPreferences.isRememberMeEnabled.first()
            if (rememberMe) {
                val savedEmail = userPreferences.savedEmail.first()
                _loginUiState.update { it.copy(email = savedEmail) }
            }

            val loggedIn = repository.isUserLoggedIn()
            if (loggedIn && !rememberMe) {
                repository.logout()
                _isUserLoggedIn.value = false
            } else {
                _isUserLoggedIn.value = loggedIn && rememberMe
            }
        }
    }

    fun onRememberMeChanged(checked: Boolean) {
        rememberMe = checked
    }

    fun onLoginEmailChanged(email: String) {
        _loginUiState.update { it.copy(email = email, emailError = null) }
    }

    fun onLoginPasswordChanged(password: String) {
        _loginUiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onRegisterEmailChanged(email: String) {
        _registerUiState.update { it.copy(email = email, emailError = null) }
    }

    fun onRegisterDisplayNameChanged(displayName: String) {
        _registerUiState.update { it.copy(displayName = displayName, displayNameError = null) }
    }

    fun onRegisterPasswordChanged(password: String) {
        _registerUiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onRegisterConfirmPasswordChanged(confirmPassword: String) {
        _registerUiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun login() {
        val state = _loginUiState.value
        val emailValidation = FormValidation.validateEmail(state.email)
        val passwordValidation = FormValidation.validatePassword(state.password)

        if (!emailValidation.isValid || !passwordValidation.isValid) {
            _loginUiState.update {
                it.copy(
                    emailError = emailValidation.errorMessage,
                    passwordError = passwordValidation.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            repository.login(state.email, state.password).collect { result ->
                if (result is Resource.Success) {
                    userPreferences.saveRememberMe(rememberMe, state.email)
                }
                _authState.value = result
            }
        }
    }

    fun register() {
        val state = _registerUiState.value
        val emailValidation = FormValidation.validateEmail(state.email)
        val displayNameValidation = FormValidation.validateDisplayName(state.displayName)
        val passwordValidation = FormValidation.validatePassword(state.password)
        val confirmPasswordValidation = FormValidation.validateConfirmPassword(state.password, state.confirmPassword)

        val hasError = !emailValidation.isValid || !displayNameValidation.isValid ||
                !passwordValidation.isValid || !confirmPasswordValidation.isValid

        if (hasError) {
            _registerUiState.update {
                it.copy(
                    emailError = emailValidation.errorMessage,
                    displayNameError = displayNameValidation.errorMessage,
                    passwordError = passwordValidation.errorMessage,
                    confirmPasswordError = confirmPasswordValidation.errorMessage
                )
            }
            return
        }

        viewModelScope.launch {
            repository.register(state.email, state.displayName, state.password).collect { result ->
                _authState.value = result
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onLogout()
            _authState.value = Resource.Idle
            _isUserLoggedIn.value = false
        }
    }

    fun resetAuthState() {
        _authState.value = Resource.Idle
    }

    fun updateLoginStatus(isLoggedIn: Boolean) {
        _isUserLoggedIn.value = isLoggedIn
    }
}
