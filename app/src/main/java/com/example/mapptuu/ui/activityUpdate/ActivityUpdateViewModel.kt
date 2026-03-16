package com.example.mapptuu.ui.activityUpdate



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.repository.activity.ActivityRepository
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
    val activityTypeId:String="",
    val createdAt: Timestamp =Timestamp.now(),
    val description:String="",
    val imageRef:String="",
    val latitude:String="",
    val longitude:String="",
    val name:String="",
    val ownerId:String="",
    val rating:Float=0F
)

@HiltViewModel
class ActivityUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val activityRepository : ActivityRepository
): ViewModel() {
    var isError =false
    var activityId = ""
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var activityTypeId by mutableStateOf("")
    var longitude by mutableStateOf("")
    var latitude by mutableStateOf("")

    var imageRef by mutableStateOf("")

    var ownerId by mutableStateOf("")
    var rating by mutableFloatStateOf(0F)

    val createdAt= Timestamp.now()



    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.ActivityUpdate>()
            activityId = route.id
            val activity = activityRepository.readOne(activityId)
            activity.let{activity ->
                name = activity.getOrNull()!!.name
                description = activity.getOrNull()!!.description
                activityTypeId = activity.getOrNull()!!.activityTypeId
                imageRef = activity.getOrNull()!!.imageRef
                ownerId = activity.getOrNull()!!.ownerId
                rating = activity.getOrNull()!!.rating
                longitude = activity.getOrNull()!!.longitude
                latitude = activity.getOrNull()!!.latitude

                _uiState.value = activity.getOrNull()!!.toDetailUiState()
            }


        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        isError = true

    }
    fun update(){
        viewModelScope.launch {
            val activity = Activity(
                id = activityId,
                name = name,
                description = description,
                imageRef = imageRef,
                activityTypeId =activityTypeId,
                createdAt = createdAt,
                latitude = latitude,
                longitude = longitude,
                ownerId = ownerId,
                rating = rating,
            )
            activityRepository.update(activity)
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

}

