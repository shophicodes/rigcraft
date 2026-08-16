package com.example.rigcraft.util

sealed interface Resource<out T> {
    object Idle : Resource<Nothing>
    object Loading : Resource<Nothing>
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val message: String, val throwable: Throwable? = null): Resource<Nothing>
}