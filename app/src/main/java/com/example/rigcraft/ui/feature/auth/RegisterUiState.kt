package com.example.rigcraft.ui.feature.auth

data class RegisterUiState(
    val email: String = "",
    val emailError: String? = null,
    val displayName: String = "",
    val displayNameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
)
