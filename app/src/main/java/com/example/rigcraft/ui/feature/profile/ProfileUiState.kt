package com.example.rigcraft.ui.feature.profile

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val selectedTab: Int = 0, // 0: Informacije o korisniku, 1: Dostavne adrese, 2: Istorija porudžbina
    val isLoading: Boolean = false,
    val message: String? = null,
    val errorMessage: String? = null,
    val showEditNameDialog: Boolean = false,
    val showEditEmailDialog: Boolean = false,
    val showEditPasswordDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
)
