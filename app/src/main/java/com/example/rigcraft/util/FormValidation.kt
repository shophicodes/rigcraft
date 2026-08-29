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
}