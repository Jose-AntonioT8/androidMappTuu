package com.example.mapptuu.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Users
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _showPhotoMenu = MutableStateFlow(false)
    val showPhotoMenu: StateFlow<Boolean> = _showPhotoMenu.asStateFlow()

    private val currentUserEmail: String?
        get() = authRepository.getCurrentUser()?.email

    val userinfo: StateFlow<Users?> = flow{
        val result = userRepository.readUserByEmail(currentUserEmail)
        emit(result)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )







    val profileImageUri: StateFlow<String?> = userRepository.getProfilePicture(currentUserEmail)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    fun onTogglePhotoMenu() {
        _showPhotoMenu.value = !_showPhotoMenu.value
    }

    fun onDismissPhotoMenu() {
        _showPhotoMenu.value = false
    }

    fun onImageSelected(uri: Uri) {
        viewModelScope.launch {
            userRepository.updateProfilePicture(
                uri = uri.toString(),
                id = currentUserEmail
            )
        }
        _showPhotoMenu.value = false
    }
    fun onLogOut() {
        viewModelScope.launch {
            authRepository.logout()

        }
    }
}
