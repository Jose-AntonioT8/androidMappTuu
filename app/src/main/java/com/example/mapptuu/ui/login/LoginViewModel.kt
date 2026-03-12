package com.example.mapptuu.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.AuthResult
import com.example.mapptuu.data.repository.user.UserRepository
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null,
            errorMessage = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null,
            errorMessage = null
        )
    }

    fun onLogin() {
        // Validar campos
        val emailError = validateEmail(_uiState.value.email)
        val passwordError = validatePassword(_uiState.value.password)

        if (emailError != null || passwordError != null) {
            _uiState.value = _uiState.value.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        // Realizar login
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            when (val result = authRepository.login(_uiState.value.email, _uiState.value.password)) {
                is AuthResult.Success -> {
                    val token = authRepository.getCurrentUserToken()
                    try {
                        userRepository.refresh()
                    } catch (e: Exception) {

                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                }
                is AuthResult.Error -> {
                    val errorMsg = getErrorMessage(result.exception)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = errorMsg
                    )
                }
            }
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El campo email es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "El email no es correcto"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "El campo contraseña es requerido"
            else -> null
        }
    }

    private fun getErrorMessage(exception: Exception): String {
        return when ((exception as? FirebaseAuthException)?.errorCode) {
            "ERROR_USER_NOT_FOUND" -> "Usuario no encontrado"
            "ERROR_INVALID_CREDENTIAL" -> "Usuario o contraseña incorrectos"
            "ERROR_INVALID_EMAIL" -> "Correo electrónico no válido"
            "ERROR_NETWORK_REQUEST_FAILED" -> "Error de conexión"
            else -> exception.message ?: "Error al iniciar sesión"
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetLoginSuccess() {
        _uiState.value = _uiState.value.copy(isLoginSuccessful = false)
    }
}