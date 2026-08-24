package com.example.rigcraft.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rigcraft.domain.repository.SeederRepository
import com.example.rigcraft.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val seederRepository: SeederRepository
) : ViewModel() {

    init {
        // seedMockData()
    }

    private fun seedMockData() {
        viewModelScope.launch {
            seederRepository.seedData().collect { result ->
                when (result) {
                    is Resource.Success -> Log.d("FirestoreSeeder", result.data)
                    is Resource.Error -> Log.e("FirestoreSeeder", result.message)
                    else -> {}
                }
            }
        }
    }
}
