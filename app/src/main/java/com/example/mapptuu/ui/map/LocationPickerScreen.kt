package com.example.mapptuu.ui.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UnrememberedMutableState")
@Composable
fun LocationPickerScreen(
    modifier: Modifier = Modifier,
    initialLat: String? = null,
    initialLng: String? = null,
    onCancel: () -> Unit,
    onConfirm: (lat: String, lng: String) -> Unit,
) {
    //coje los valores que le pasamos a la pantalla como valores principales o vacía las variables
    val initialLatDouble = initialLat?.toDoubleOrNull()
    val initialLngDouble = initialLng?.toDoubleOrNull()
    //valores de inicio para la variable selected, cuando se habra el mapa, el picker tendra estos valores por defecto y luego podremos ponerlo donde queramos
    val defaultCenter = LatLng(36.7292241198057, -4.549024850346634)
    val initialCenter = if (initialLatDouble != null && initialLngDouble != null) {
        LatLng(initialLatDouble, initialLngDouble)
    } else {
        defaultCenter
    }
    //selected es nuestro valor de longitud y latitud que selecionamos para devolverlo al ViewModel
    var selected by remember { mutableStateOf<LatLng?>(initialCenter) }
    //posicion inicial del mapa
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialCenter, 15f)
    }

    Scaffold(
        topBar = { Header(title = stringResource(R.string.selectUbication)) },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            //Mapa
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraState,
                properties = MapProperties(
                    isTrafficEnabled = true,
                    mapType = MapType.HYBRID,
                ),
                //si clicamos en el mapa, cambia el valor de LatLng y se lo pasamos a nuestra variable selected, para poner en el mapa el marcador
                onMapClick = { latLng ->
                    selected = latLng
                },
            ) {
                //segun el valor de selected ponemos un marcador en el mapa
                selected?.let {
                    Marker(
                        state = MarkerState(position = it),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = onCancel,
                ) {
                    Text(stringResource(R.string.cancel))
                }

                Button(
                    //si le damos al boton de confirmar, enviamos los valores de nuestra variable selected al viewmodel en forma de latitud y longitud
                    onClick = {
                        onConfirm(selected!!.latitude.toString(), selected!!.longitude.toString())
                    },
                ) {
                    Text(stringResource(R.string.confirm))
                }
            }
        }
    }
}

