package com.example.mapptuu.ui.activityDetail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.navigation.toRoute
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.ui.navigation.Route
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp


data class DetailUiState(
    val id:Long=0L,
    val activityTypeId:String="",
    val createdAt: Timestamp=Timestamp.now(),
    val description:String="",
    val imageRef:String="",
    val latitude:String="",
    val longitude:String="",
    val name:String="",
    val ownerId:String="",
    val rating:Int=0
)

@HiltViewModel
class ActivityDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val activityRepository : ActivityRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.ActivityDetail>()
            val activityId = route.id.toLong()
            val activity = activityRepository.readOne(activityId)
            activity?.let{
                _uiState.value = activity.getOrNull()!!.toDetailUiState()
            }

        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    }
    fun delete(id:Long){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            activityRepository.delete(id)
        }
    }

}

fun Activity.toDetailUiState(): DetailUiState = DetailUiState(
    name = this.name,
    id = this.id,
    imageRef = this.imageRef,
    activityTypeId = this.activityTypeId,
    createdAt = this.createdAt,
    description = this.description,
    latitude = this.latitude,
    longitude = this.longitude,
    ownerId = this.ownerId,
    rating = this.rating,
)
