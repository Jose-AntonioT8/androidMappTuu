package com.example.mapptuu.ui.planCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.data.repository.plan.PlanRepository
import com.example.mapptuu.ui.planDetail.DetailUiState
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planRepository: PlanRepository,
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository,
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            activityRepository.refresh()
            activityRepository.observe().collectLatest { result ->
                if (result.isSuccess) {
                    activities = result.getOrNull().orEmpty()
                }
            }
        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    }
    fun create(){
        viewModelScope.launch {
            val plan = Plans(
                id = "",
                name = name,
                description = description,
                imgRef = "https://dragonball-api.com/characters/vegeta_normal.webp",
                createdAt = Timestamp.now(),
                ownerId = authRepository.getCurrentUserToken()!!,
                rating = 0F,
                activitiesIds = selectedActivityIds.toList(),
                visibility = visibility,
            )
            planRepository.insert(plan)
        }
    }
    var isError =false
    var visibility by mutableStateOf(false)
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var activities by mutableStateOf<List<Activity>>(emptyList())
    var selectedActivityIds by mutableStateOf<Set<String>>(emptySet())

    fun toggleActivitySelection(activityId: String) {
        selectedActivityIds =
            if (selectedActivityIds.contains(activityId)) selectedActivityIds - activityId
            else selectedActivityIds + activityId
    }

}

