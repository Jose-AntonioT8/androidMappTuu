package com.example.mapptuu.ui.singup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Users
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.AuthResult
import com.example.mapptuu.data.repository.user.UserRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val emailError: String? = null,
    val nameError: String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isSignUpSuccessful: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null,
            errorMessage = null
        )
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            nameError = null,
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

    fun onRepeatPasswordChange(repeatPassword: String) {
        _uiState.value = _uiState.value.copy(
            repeatPassword = repeatPassword,
            repeatPasswordError = null,
            errorMessage = null
        )
    }

    fun onSignUp() {
        // Validar campos
        val emailError = validateEmail(_uiState.value.email)
        val nameError = validateName(_uiState.value.name)
        val passwordError = validatePassword(_uiState.value.password)
        val repeatPasswordError = validateRepeatPassword(
            _uiState.value.password,
            _uiState.value.repeatPassword
        )

        if (emailError != null || nameError != null ||
            passwordError != null || repeatPasswordError != null) {
            _uiState.value = _uiState.value.copy(
                emailError = emailError,
                nameError = nameError,
                passwordError = passwordError,
                repeatPasswordError = repeatPasswordError
            )
            return
        }

        // Realizar registro
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null
            )

            when (val result = authRepository.register(
                _uiState.value.email,
                _uiState.value.password
            )) {
                is AuthResult.Success -> {

                    // Lo pasamos a Users
                    val user = result.user

                    val newUser = Users(
                        id = user?.uid ?: "",
                        name = _uiState.value.name,
                        email = _uiState.value.email,
                        createdAt = Timestamp.now(),
                        photoUri = null,
                    )
                    //Aqui lo guardamos el usuario creado en local y remorto
                    userRepository.insert(newUser)

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Cuenta creada correctamente",
                        isSignUpSuccessful = true
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
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "El email no es correcto"
            else -> null
        }
    }

    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "El campo nombre es requerido"
            name.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "El campo contraseña es requerido"
            else -> null
        }
    }

    private fun validateRepeatPassword(password: String, repeatPassword: String): String? {
        return when {
            repeatPassword.isBlank() -> "El campo contraseña es requerido"
            password != repeatPassword -> "Las contraseñas no son iguales"
            else -> null
        }
    }

    private fun getErrorMessage(exception: Exception): String {
        return when ((exception as? FirebaseAuthException)?.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> "Este correo ya está registrado"
            "ERROR_INVALID_EMAIL" -> "Correo electrónico no válido"
            "ERROR_WEAK_PASSWORD" -> "La contraseña es demasiado débil (mínimo 6 caracteres)"
            "ERROR_NETWORK_REQUEST_FAILED" -> "Error de conexión"
            else -> exception.message ?: "Error al crear la cuenta"
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetSignUpSuccess() {
        _uiState.value = _uiState.value.copy(isSignUpSuccessful = false)
    }
}