package com.example.rigcraft.ui.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.data.model.AddressDto
import com.example.rigcraft.domain.repository.ProfileRepository
import com.example.rigcraft.util.FormValidation
import com.example.rigcraft.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authFirebase: FirebaseAuth,
): ViewModel() {
    private val currentUserId: String get() = authFirebase.currentUser?.uid ?: ""

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserData()
        observeAddresses()
    }

    private fun loadUserData() {
        val user = authFirebase.currentUser
        _uiState.update {
            it.copy(
                userName = user?.displayName ?: "Korisnik",
                userEmail = user?.email ?: ""
            )
        }
    }

    private fun observeAddresses() {
        if (currentUserId.isEmpty()) return

        viewModelScope.launch {
            profileRepository.getAddresses(currentUserId).collect { res ->
                if (res is Resource.Success) {
                    _uiState.update { it.copy(addresses = res.data) }
                } else if (res is Resource.Error) {
                    _uiState.update { it.copy(errorMessage = res.message) }
                }
            }
        }
    }

    fun selectTab(index: Int) {
        _uiState.update {
            it.copy(selectedTab = index)
        }
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            val res = profileRepository.updateName(newName)
            if (res is Resource.Success) {
                _uiState.update { it.copy(userName = newName, showEditNameDialog = false, message = "Ime uspešno promenjeno!", errorMessage = null) }
            } else if (res is Resource.Error) {
                _uiState.update { it.copy(errorMessage = res.message, message = null) }
            }
        }
    }
    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            val res = profileRepository.updatePassword(newPassword)
            if (res is Resource.Success) {
                _uiState.update { it.copy(showEditPasswordDialog = false, message = "Lozinka uspešno promenjena!", errorMessage = null) }
            } else if (res is Resource.Error) {
                _uiState.update { it.copy(errorMessage = res.message, message = null) }
            }
        }
    }

    fun deleteAccount(onDeleted: () -> Unit) {
        viewModelScope.launch {
            val res = profileRepository.deleteAccount()
            if (res is Resource.Success) onDeleted()
            else if (res is Resource.Error) _uiState.update { it.copy(errorMessage = res.message, message = null) }
        }
    }

    fun toggleNameDialog(show: Boolean) {
        _uiState.update {
            it.copy(showEditNameDialog = show)
        }
    }
    fun togglePasswordDialog(show: Boolean) {
        _uiState.update {
            it.copy(showEditPasswordDialog = show)
        }
    }
    fun toggleDeleteDialog(show: Boolean) {
        _uiState.update {
            it.copy(showDeleteAccountDialog = show)
        }
    }

    fun onAddressNameChanged(name: String) {
        _uiState.update { it.copy(addressFormState = it.addressFormState.copy(name = name, nameError = null)) }
    }

    fun onAddressPhoneChanged(phone: String) {
        _uiState.update { it.copy(addressFormState = it.addressFormState.copy(phoneNumber = phone, phoneError = null)) }
    }

    fun onAddressStreetChanged(street: String) {
        _uiState.update { it.copy(addressFormState = it.addressFormState.copy(street = street, streetError = null)) }
    }

    fun onAddressCityChanged(city: String) {
        _uiState.update { it.copy(addressFormState = it.addressFormState.copy(city = city, cityError = null)) }
    }

    fun onAddressZipChanged(zip: String) {
        _uiState.update { it.copy(addressFormState = it.addressFormState.copy(zip = zip, zipError = null)) }
    }

    fun saveAddress() {
        val form = _uiState.value.addressFormState
        val addressToEdit = _uiState.value.addressToEdit ?: return

        val nameRes = FormValidation.validateFullName(form.name)
        val phoneRes = FormValidation.validatePhone(form.phoneNumber)
        val streetRes = FormValidation.validateAddress(form.street)
        val cityRes = FormValidation.validateCity(form.city)
        val zipRes = FormValidation.validateZip(form.zip)

        if (!nameRes.isValid || !phoneRes.isValid || !streetRes.isValid || !cityRes.isValid || !zipRes.isValid) {
            _uiState.update {
                it.copy(
                    addressFormState = it.addressFormState.copy(
                        nameError = nameRes.errorMessage,
                        phoneError = phoneRes.errorMessage,
                        streetError = streetRes.errorMessage,
                        cityError = cityRes.errorMessage,
                        zipError = zipRes.errorMessage
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            val res = profileRepository.saveAddress(
                currentUserId,
                addressToEdit.copy(
                    fullName = form.name,
                    phoneNumber = form.phoneNumber,
                    street = form.street,
                    city = form.city,
                    zip = form.zip
                )
            )
            if (res is Resource.Success) {
                _uiState.update { it.copy(addressToEdit = null, message = "Adresa sačuvana!", errorMessage = null) }
            } else if (res is Resource.Error) {
                _uiState.update { it.copy(errorMessage = res.message, message = null) }
            }
        }
    }

    fun editAddress(address: AddressDto?) {
        _uiState.update {
            it.copy(
                addressToEdit = address,
                addressFormState = if (address != null) {
                    AddressFormState(
                        name = address.fullName,
                        phoneNumber = address.phoneNumber,
                        street = address.street,
                        city = address.city,
                        zip = address.zip
                    )
                } else {
                    AddressFormState()
                }
            )
        }
    }

    fun deleteAddress(addressId: String) {
        viewModelScope.launch {
            val res = profileRepository.deleteAddress(currentUserId, addressId)
            if (res is Resource.Error) {
                _uiState.update { it.copy(errorMessage = res.message, message = null) }
            }
        }
    }

    fun clearMessages() = _uiState.update { it.copy(message = null, errorMessage = null) }
}