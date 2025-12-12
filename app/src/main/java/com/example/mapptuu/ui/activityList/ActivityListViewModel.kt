package com.example.mapptuu.ui.activityList

import com.example.mapptuu.data.repository.activity.ActivityRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Activity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivityListViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState : MutableStateFlow<ListUiState> =
        MutableStateFlow( ListUiState.Initial)
    val uiState : StateFlow<ListUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            activityRepository.observe().collect { result ->
                if (result.isSuccess) {
                    val activities = result.getOrNull()!!
                    if (activities.isNotEmpty()){
                        val uiActivities = activities.asListUiState()
                        _uiState.value = ListUiState.Succes(uiActivities)
                    }else {
                        activityRepository.refresh()
                        activityRepository.observe().collect { result ->
                            val activities = result.getOrNull()!!
                            if (activities.isNotEmpty()) {
                                val uiActivities = activities.asListUiState()
                                _uiState.value = ListUiState.Succes(uiActivities)
                            }
                        }
                    }

                } else {
                    _uiState.value = ListUiState.Error("No se han cargado las actividades")
                }
            }
        }
    }

    var busquedaParametros by mutableStateOf("")
        private set

    private fun acceptSearch():Boolean {
        if(busquedaParametros.isNotBlank()) {
            return true
        }
        return false
    }
    fun onBusquedaChanged(nuevoTexto: String) {
        busquedaParametros = nuevoTexto
    }
    fun search(){
        if(acceptSearch()){
            viewModelScope.launch {
                _uiState.value = ListUiState.Loading
                try {
                    val nombre = busquedaParametros.toString()
                    val activitiesByName = activityRepository.readdOneByName(nombre)
                    val respuestaCorrecta = ListUiState.Succes(
                        activitiesByName.toModel()
                    )
                    _uiState.value = respuestaCorrecta
                } catch (e: Exception) {
                    _uiState.value = ListUiState.Error("Error al cargar, no hay ninguna actividad con ese nombre: ${e.message}")
                }
            }
        } else {
            _uiState.value = ListUiState.Error("Introduce un nombre para buscar la actividad")        }
    }
}


sealed class ListUiState{
    object Initial: ListUiState()
    object Loading : ListUiState()
    data class Error(val message :String): ListUiState()

    data class Succes (
        val activities : List<ListItemUiState>
    ): ListUiState()
}
data class ListItemUiState(
    val id:String,
    val name:String,
    val image: String
)

fun Result<List<Activity>>.toModel(): List<ListItemUiState>{
    return this.getOrNull()!!.map(Activity::asListItemUiState)
}
fun Activity.asListItemUiState(): ListItemUiState{
    return ListItemUiState(
        id = this.id,
        name = this.name,
        image = this.imageRef
    )
}
fun List<Activity>.asListUiState():List<ListItemUiState>
        =this.map(Activity::asListItemUiState)

