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
                errorMessage = "Email can't be empty."
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Invalid email format."
            )
        }
        return ValidationResult(isValid = true)
    }

    fun validateDisplayName(displayName: String): ValidationResult {
        if(displayName.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Display name can't be empty."
            )
        }
        if(displayName.length > 40) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Display name mustn't have more than 40 characters."
            )
        }

        return ValidationResult(isValid = true)
    }

    fun validatePassword(password: String): ValidationResult {
        if(password.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password can't be empty."
            )
        }
        if(password.length < 6) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password must have at least 6 characters."
            )
        }
        return ValidationResult(isValid = true)
    }

    fun validateConfirmPassword(password: String, confirmPass: String): ValidationResult {
        if(password != confirmPass) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Passwords do not match."
            )
        }
        return ValidationResult(isValid = true)
    }
}