package com.example.mapptuu.ui.planUpdate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.data.repository.plan.PlanRepository
import com.example.mapptuu.ui.navigation.Route
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailUiState(
    val id:String="",
    val activityIds:List<String> = listOf(),
    val createdAt: Timestamp=Timestamp.now(),
    val description:String="",
    val imageRef:String="",
    val visibility:Boolean=false,
    val name:String="",
    val ownerId:String="",
    val rating:Float=0F
)

@HiltViewModel
class PlanUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planRepository: PlanRepository
): ViewModel() {
    var isError =false
    var visibility by mutableStateOf(false)
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var activitiesIdInput by mutableStateOf("")

    var createdAt= Timestamp.now()

    var rating =0F
    var ownerId by mutableStateOf("")
    var imgRef by mutableStateOf("")
    var planId =""
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.PlanUpdate>()
            planId = route.id
            val plan = planRepository.readOne(planId)
            plan.let{plan ->
                name = plan.getOrNull()!!.name
                description = plan.getOrNull()!!.description
                activitiesIdInput = plan.getOrNull()!!.activityIds.joinToString(", ")
                imgRef = plan.getOrNull()!!.imgRef
                ownerId = plan.getOrNull()!!.ownerId
                rating = plan.getOrNull()!!.rating
                visibility = plan.getOrNull()!!.visibility
                _uiState.value = plan.getOrNull()!!.toDetailUiState()
            }


        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        isError = true

    }
    fun update(){
        var activityIds: List<String> = listOf()
        activityIds = activitiesIdInput
            .split(',')
            .map { it.trim() }
            .filter { it.isNotEmpty() }
        viewModelScope.launch {
            val plan = Plans(
                id = planId,
                name = name,
                description = description,
                imgRef = imgRef,
                activityIds = activityIds,
                createdAt = createdAt,
                visibility = visibility,
                ownerId = ownerId,
                rating = rating,
            )
            planRepository.insert(plan)
        }
    }

    fun Plans.toDetailUiState(): DetailUiState = DetailUiState(
        name = this.name,
        id = this.id,
        imageRef = this.imgRef,
        activityIds = this.activityIds,
        createdAt = this.createdAt,
        description = this.description,
        ownerId = this.ownerId,
        visibility = this.visibility,
        rating = this.rating,
    )

}

