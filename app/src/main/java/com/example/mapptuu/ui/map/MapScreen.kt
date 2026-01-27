package com.example.mapptuu.ui.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mapptuu.ui.component.Footer
import com.example.mapptuu.ui.component.Header

import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(viewModel: MapViewModel = viewModel(),
              modifier: Modifier,
              navController: NavController,
              onPlanList: () -> Unit,
              onNavigateToSetting: () -> Unit,
              onNavigateToActivities: () -> Unit,
              onNavigateToProfile: () -> Unit
) {

    // Configuración inicial de cámara (Zoom 15)
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.malaga, 15f)
    }

    Scaffold(
        topBar = {
            Header() {  }
        },
        bottomBar = {
            Footer(
                activeRoute = "mapa",
                onNavigate = { route ->
                    when (route) {
                        "lista" -> onNavigateToActivities()
                        "plans" -> onPlanList()
                        "ajustes" -> onNavigateToSetting()
                    }
                },
                navController = navController
            )
        }
    ) { innerPadding ->
        // El Mapa
        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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
}