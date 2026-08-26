package com.example.rigcraft.ui.feature.details

sealed class DetailsUiEvent {
    data class ShowToast(val message: String) : DetailsUiEvent()
}