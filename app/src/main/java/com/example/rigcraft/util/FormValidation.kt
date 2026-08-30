package com.example.rigcraft.util

import android.util.Patterns

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object FormValidation {
    fun validateEmail(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Email adresa ne može biti prazna."
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Email format nije dobar."
            )
        }
        return ValidationResult(isValid = true)
    }

    fun validateDisplayName(displayName: String): ValidationResult {
        if(displayName.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Ime ne može biti prazno."
            )
        }
        if(displayName.length > 40) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Ime ne može imati više od 40 karaktera."
            )
        }

        return ValidationResult(isValid = true)
    }

    fun validatePassword(password: String): ValidationResult {
        if(password.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Lozinka ne može biti prazna."
            )
        }
        if(password.length < 6) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Lozinka mora imati minimum 6 karaktera."
            )
        }
        return ValidationResult(isValid = true)
    }

    fun validateConfirmPassword(password: String, confirmPass: String): ValidationResult {
        if(password != confirmPass) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Lozinke se ne poklapaju."
            )
        }
        return ValidationResult(isValid = true)
    }

    fun validatePhone(phone: String): ValidationResult {
        if(phone.isBlank()) {
            return ValidationResult(false, "Broj telefona je obavezan.")
        }
        if(!Patterns.PHONE.matcher(phone).matches()) {
            return ValidationResult(false, "Neispravan format broja telefona.")
        }
        return ValidationResult(true)
    }

    fun validateZip(zip: String): ValidationResult {
        if(zip.isBlank()) {
            return ValidationResult(false, "Poštanski broj je obavezan.")
        }
        if(!zip.matches(Regex("^[0-9]{5}$"))) {
            return ValidationResult(false, "Poštanski broj mora imati 5 cifara.")
        }
        return ValidationResult(true)
    }

    fun validateFullName(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(false, "Ime i prezime je obavezno.")
        }
        if(name.length > 50) {
            return ValidationResult(false, "Ime i prezime ne sme biti duže od 50 karaktera.")
        }
        return ValidationResult(true)
    }

    fun validateAddress(address: String): ValidationResult {
        if(address.isBlank()) {
            return ValidationResult(false, "Adresa je obavezna.")
        }
        if(address.length > 50) {
            return ValidationResult(false, "Adresa ne sme biti duža od 50 karaktera.")
        }
        return ValidationResult(true)
    }

    fun validateCity(city: String): ValidationResult {
        if(city.isBlank()) {
            return ValidationResult(false, "Mesto je obavezno.")
        }
        if(city.length > 25) {
            return ValidationResult(false, "Mesto ne sme biti duže od 25 karaktera.")
        }
        return ValidationResult(true)
    }
}