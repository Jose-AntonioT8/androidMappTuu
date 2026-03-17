package com.example.mapptuu.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
): ViewModel() {

    private val _activities = MutableStateFlow<List<Activity>>(emptyList())

    val activities: StateFlow<List<Activity>> = _activities.asStateFlow()
    val malaga = LatLng(36.7292241198057, -4.549024850346634)

    init {
        loadActivity()
    }

    fun loadActivity() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                activityRepository.readAll()
            }
            result.onSuccess { activityList ->
                _activities.value = activityList
            }
        }
    }

}