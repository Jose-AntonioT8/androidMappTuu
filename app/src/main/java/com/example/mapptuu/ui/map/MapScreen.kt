package com.example.mapptuu.ui.map

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mapptuu.ui.component.Footer
import com.example.mapptuu.ui.component.Header
import com.example.mapptuu.ui.navigation.navigateToActivityDetail

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel(),
              modifier: Modifier,
              navController: NavController,
              onPlanList: () -> Unit,
              onNavigateToSetting: () -> Unit,
              onNavigateToActivities: () -> Unit,
              onNavigateToProfile: () -> Unit,
              onNavigateToLanding: () -> Unit,
) {
    val activities by viewModel.activities.collectAsState()


    // Configuración inicial de cámara (Zoom 15)
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(viewModel.malaga, 15f)
    }

    Scaffold(
        topBar = {
            Header(onMenuClick = onNavigateToLanding) {  }
        },
        bottomBar = {
            Footer(
                activeRoute = "mapa",
                onNavigate = { route ->
                    when (route) {
                        "lista" -> onNavigateToActivities()
                        "plans" -> onPlanList()
                        "profile" -> onNavigateToProfile()
                    }
                },
                navController = navController
            )
        }
    ) { innerPadding ->
        // El Mapa
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            cameraPositionState = cameraState,
            properties = MapProperties(
                isTrafficEnabled = true,
                mapType = MapType.HYBRID
            )
        ) {


            activities.forEach {activity ->
                    val lat = activity.latitude.toDoubleOrNull()
                    val long = activity.longitude.toDoubleOrNull()

                    if(lat != null && long != null){
                        Marker(
                            state = MarkerState(position = LatLng(lat, long)),
                            onClick = {
                                navController.navigateToActivityDetail(activity.id.toString())
                                true
                            }
                        )
                    }

               }
            }


        }
    }