package com.example.mapptuu.ui.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(viewModel: MapViewModel = viewModel()) {

    // Configuración inicial de cámara (Zoom 15 en Kyoto)
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.malaga, 15f)
    }

    // El Mapa
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(
            isTrafficEnabled = true,
            mapType = MapType.HYBRID
        )
    ) {
        Log.d("DEBUG_MAPA", "Intentando pintar ${viewModel.places.size} marcadores")
        viewModel.places.forEach { place ->

            Marker(
                state = MarkerState(position = place.latLng),
                title = place.name
            )
        }

    }
}