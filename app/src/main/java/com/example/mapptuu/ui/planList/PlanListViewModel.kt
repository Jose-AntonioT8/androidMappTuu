package com.example.mapptuu.ui.planList

import com.example.mapptuu.data.repository.plan.PlanRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Plans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class PlanListViewModel@Inject constructor(
    private val planRepository: PlanRepository,
): ViewModel() {
    private var fullList: List<ListItemUiState> = emptyList()

    private val _uiState : MutableStateFlow<ListUiState> =
        MutableStateFlow( ListUiState.Initial)
    val uiState : StateFlow<ListUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            //Aqui refresca para traer datos de remoto a local
            try {
                _uiState.value = ListUiState.Loading
                planRepository.refresh()
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error("Error al refrescar planes: ${e.message}")
            }

            try {
                planRepository.observe().collect { result ->
                    if (result.isSuccess) {
                        val plans = result.getOrNull()!!
                        val uiPlans = plans.asListUiState()
                        fullList = uiPlans
                        _uiState.value = ListUiState.Succes(uiPlans)
                    } else {
                        val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                        _uiState.value = ListUiState.Error("Error al cargar planes: $error")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error("Excepción al cargar planes: ${e.message}")
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
                val query = busquedaParametros.trim().lowercase()

                val palabras = query.split("\\s+".toRegex()).filter { it.isNotEmpty()}


                val resultadosFiltrados = fullList.filter { plan ->
                    val nombrePlan = plan.name.lowercase()
                    palabras.any{ palabras ->
                        nombrePlan.contains(palabras)
                    }
                }
                if (resultadosFiltrados.isEmpty()) {
                    _uiState.value = ListUiState.Error("No se encontraron planes")
                } else {
                    _uiState.value = ListUiState.Succes(resultadosFiltrados)
                }
            }
        } else {
            _uiState.value = ListUiState.Error("Introduce un nombre para buscar el plan")        }
    }
}


sealed class ListUiState{
    object Initial: ListUiState()
    object Loading : ListUiState()
    data class Error(val message :String): ListUiState()

    data class Succes (
        val plans : List<ListItemUiState>
    ): ListUiState()
}
data class ListItemUiState(
    val id:String,
    val name:String,
    val rating: Float,
    val image: String
)

fun Result<List<Plans>>.toModel(): List<ListItemUiState>{
    return this.getOrNull()!!.map(Plans::asListItemUiState)
}
fun Plans.asListItemUiState(): ListItemUiState{
    return ListItemUiState(
        id = this.id,
        name = this.name,
        image = this.imgRef,
        rating = this.rating
    )
}
fun List<Plans>.asListUiState():List<ListItemUiState>
        =this.map(Plans::asListItemUiState)

