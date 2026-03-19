package com.example.mapptuu.ui.planDetail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.navigation.toRoute
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.data.repository.AuthRepository
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.data.repository.plan.PlanRepository
import com.example.mapptuu.ui.navigation.Route
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp

data class DetailUiState(
    val id:String="",
    val activitiesIds:List<String>? = listOf(),
    val activityNames: List<String> = emptyList(),
    val createdAt: Timestamp=Timestamp.now(),
    val description:String="",
    val imageRef:String="",
    val visibility:Boolean=false,
    val name:String="",
    val ownerId:String="",
    val rating:Float=0F,
    val isOwner: Boolean = false
)

@HiltViewModel
class ActivityDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planRepository : PlanRepository,
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository,
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.PlanDetail>()
            val activityId = route.id
            val activity = planRepository.readOne(activityId)
            activity.let{
                val detail = activity.getOrNull()!!.toDetailUiState()
                val names = detail.activitiesIds
                    .orEmpty()
                    .map { id ->
                        activityRepository.readOne(id).getOrNull()?.name ?: id
                    }
                _uiState.value = detail.copy(activityNames = names)

            val currentUserToken = authRepository.getCurrentUser()?.uid
            val isOwner = detail.ownerId == currentUserToken
                _uiState.value = detail.copy(
                    isOwner = isOwner
                )
        }

        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    }
    fun delete(id:String){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            planRepository.delete(id)
        }
    }

}

fun Plans.toDetailUiState(): DetailUiState = DetailUiState(
    name = this.name,
    id = this.id,
    imageRef = this.imgRef,
    activitiesIds = this.activitiesIds,
    createdAt = this.createdAt,
    description = this.description,
    ownerId = this.ownerId,
    visibility = this.visibility,
    rating = this.rating,
)
