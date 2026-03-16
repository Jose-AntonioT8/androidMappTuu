package com.example.mapptuu.ui.planCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.plan.PlanRepository
import com.example.mapptuu.ui.planDetail.DetailUiState
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planRepository: PlanRepository,
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
        var activityIds: List<String> = listOf()
        activityIds = activitiesNamesInput
            .split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        viewModelScope.launch {
            val plan = Plans(
                id = "",
                name = name,
                description = description,
                imgRef = "https://dragonball-api.com/characters/vegeta_normal.webp",
                createdAt = Timestamp.now(),
                ownerId = authRepository.getCurrentUserToken()!!,
                rating = 0F,
                activityIds = activityIds,
                visibility = visibility,
            )
            planRepository.insert(plan)
        }
    }
    var isError =false
    var visibility by mutableStateOf(false)
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var activitiesNamesInput by mutableStateOf("")

}

