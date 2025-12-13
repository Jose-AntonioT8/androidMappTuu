package com.example.mapptuu.ui.landingPage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class LandingPageViewModel@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository

): ViewModel() {
    var isLogged =false
    init{
        viewModelScope.launch {
            isLogged=authRepository.isAuthenticated()
        }
    }


    fun onLogOut(){
        viewModelScope.launch {

            authRepository.logout()
        }
    }
}