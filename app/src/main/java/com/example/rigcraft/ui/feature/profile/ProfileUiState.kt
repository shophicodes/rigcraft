package com.example.rigcraft.ui.feature.profile

import com.example.rigcraft.data.model.AddressDto

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val addresses: List<AddressDto> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
    val errorMessage: String? = null,
    val showEditNameDialog: Boolean = false,
    val showEditEmailDialog: Boolean = false,
    val showEditPasswordDialog: Boolean = false,
    val showDeleteAccountDialog: Boolean = false,
    val addressToEdit: AddressDto? = null,
    val addressFormState: AddressFormState = AddressFormState()
)

data class AddressFormState(
    val name: String = "",
    val phoneNumber: String = "",
    val street: String = "",
    val city: String = "",
    val zip: String = "",
    val nameError: String? = null,
    val phoneError: String? = null,
    val streetError: String? = null,
    val cityError: String? = null,
    val zipError: String? = null
)
