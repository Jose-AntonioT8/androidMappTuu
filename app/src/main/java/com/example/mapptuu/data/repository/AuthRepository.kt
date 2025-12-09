package com.example.mapptuu.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthResult {
    data class Success(val user: FirebaseUser?) : AuthResult()
    data class Error(val exception: Exception) : AuthResult()
}

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    // Observable del estado del usuario autenticado
    val currentUser: Flow<FirebaseUser?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(authStateListener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    // Usuario actual sincrónico
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    // Login con email y contraseña
    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    // Registro con email y contraseña
    suspend fun register(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    // Logout
    suspend fun logout() {
        firebaseAuth.signOut()
    }

    // Verificar si el usuario está autenticado
    fun isAuthenticated(): Boolean = firebaseAuth.currentUser != null

    // Verificar si es admin
    fun isAdmin(): Boolean {
        return firebaseAuth.currentUser?.email == "admin@mapptuu.com"
    }

    // Obtener token del usuario actual
    suspend fun getCurrentUserToken(): String? {
        return try {
            firebaseAuth.currentUser?.getIdToken(false)?.await()?.token
        } catch (e: Exception) {
            null
        }
    }
}