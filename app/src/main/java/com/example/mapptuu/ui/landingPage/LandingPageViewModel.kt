package com.example.mapptuu.ui.landingPage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class LandingPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository

): ViewModel() {
    var isLogged by mutableStateOf(false)
        private set

    init {
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            isLogged = authRepository.isAuthenticated()
        }
    }

    fun onLogOut() {
        viewModelScope.launch {
            authRepository.logout()
            isLogged = false
        }
    }
}