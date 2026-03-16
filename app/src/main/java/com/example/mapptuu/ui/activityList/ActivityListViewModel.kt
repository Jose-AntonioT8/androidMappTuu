    package com.example.mapptuu.ui.activityList

    import com.example.mapptuu.data.repository.activity.ActivityRepository
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.mapptuu.data.model.Activity
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import javax.inject.Inject
    @HiltViewModel

    class ActivityListViewModel @Inject constructor(
        private val activityRepository: ActivityRepository,
    ): ViewModel() {

        private var fullList: List<ListItemUiState> = emptyList()
        private val _uiState : MutableStateFlow<ListUiState> =
            MutableStateFlow( ListUiState.Initial)
        val uiState : StateFlow<ListUiState>
            get()= _uiState.asStateFlow()
        init {
            viewModelScope.launch {
                //Aqui refresca para traer datos de remoto a local
                 //TODO: Esto en un futuro haceerlo con workermanager
                try {
                    _uiState.value = ListUiState.Loading
                    activityRepository.refresh()
                } catch (e: Exception) {
                    _uiState.value = ListUiState.Error("Error al refrescar actividades: ${e.message}")
                }

                try {
                    activityRepository.observe().collect { result ->
                        if (result.isSuccess) {
                            val activities = result.getOrNull()!!
                            val uiActivities = activities.asListUiState()
                            fullList = uiActivities
                            _uiState.value = ListUiState.Succes(uiActivities)
                            } else {
                            val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                            _uiState.value = ListUiState.Error("Error al cargar actividades: $error")                     }
                        }
                } catch (e: Exception) {
                    _uiState.value = ListUiState.Error("Excepción al cargar actividades: ${e.message}")
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
                val query = busquedaParametros.trim().lowercase()

                val palabras = query.split("\\s+".toRegex()).filter { it.isNotEmpty()}


                val resultadosFiltrados = fullList.filter { actividad ->
                    val nombreActividad = actividad.name.lowercase()
                    palabras.any{ palabras ->
                        nombreActividad.contains(palabras)
                    }
                }
                if (resultadosFiltrados.isEmpty()) {
                    _uiState.value = ListUiState.Error("No se encontraron actividades")
                } else {
                    _uiState.value = ListUiState.Succes(resultadosFiltrados)
                }
            } else {
                _uiState.value = ListUiState.Succes(fullList)
            }
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
        val id: String,
        val name: String,
        val image: String,
        val rating: Float,
        val location: String,
    )

    fun Result<List<Activity>>.toModel(): List<ListItemUiState>{
        return this.getOrNull()!!.map(Activity::asListItemUiState)
    }
    fun Activity.asListItemUiState(): ListItemUiState{
        return ListItemUiState(
            id = this.id,
            name = this.name,
            image = this.imageRef,
            rating = this.rating,
            location = this.longitude,
        )
    }
    fun List<Activity>.asListUiState():List<ListItemUiState>
            =this.map(Activity::asListItemUiState)

