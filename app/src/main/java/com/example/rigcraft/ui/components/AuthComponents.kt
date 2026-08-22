package com.example.rigcraft.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.rigcraft.R

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: Painter? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isPassword: Boolean = false,
    singleLine: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(text = errorMessage, color = MaterialTheme.colorScheme.error) }
        } else null,
        leadingIcon = leadingIcon?.let {
            { Icon(painter = it, contentDescription = null) }
        },
        trailingIcon = if (isPassword) {
            {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.visibility_off_24px)
                else
                    painterResource(id = R.drawable.visibility_24px)

                val description = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = description)
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine
    )
}

@Composable
fun LoadingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled && !isLoading
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimensionResource(R.dimen.progress_indicator_size)),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = dimensionResource(R.dimen.progress_indicator_stroke)
                )
            } else {
                Text(text = text)
            }
        }
    }
}
