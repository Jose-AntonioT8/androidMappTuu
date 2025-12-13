package com.example.mapptuu.ui.activityCreation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.ui.activityDetail.DetailUiState
import com.google.firebase.Timestamp

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val activityRepository: ActivityRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {

    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    }
    fun create(){
        viewModelScope.launch {
            val newId = getLastId()
            val activity = Activity(
                id = newId,
                name = name,
                description = description,
                imageRef = "https://dragonball-api.com/characters/vegeta_normal.webp",
                activityTypeId = activityTypeId,
                createdAt = Timestamp.now(),
                latitude = latitude,
                longitude = longitude,
                ownerId = authRepository.getCurrentUserToken()!!,
                rating = 0,
            )
            activityRepository.insert(activity)
        }
    }
    private suspend fun getLastId(): Long {
        val characters = activityRepository.observe().first().getOrNull()
        val maxId = characters?.maxOfOrNull { it.id } ?: 0L
        return maxId + 1
    }
    var isError =false
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var activityTypeId by mutableStateOf("")
    var longitude by mutableStateOf("")
    var latitude by mutableStateOf("")

}

