package com.example.mapptuu.ui.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng



class MapViewModel : ViewModel() {
    val malaga = LatLng(36.7292241198057, -4.549024850346634)

    // Tu lista de lugares (Simulada o desde base de datos)
    val places = listOf(
        Place("Torre Eiffel", LatLng(35.6895, 13.6917)),
        Place("Estatua Libertad", LatLng(40.6892, -54.0445)),
        Place("Muelle Uno", LatLng(36.7292241198057, -4.549024850346634))
    )

}
// Data class simple para tus datos
data class Place(val name: String, val latLng: LatLng)