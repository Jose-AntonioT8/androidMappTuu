package com.example.mapptuu.ui.component



import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mapptuu.R
@Composable
fun Footer(
    onNavigate: (String) -> Unit
){
    NavigationBar {
        NavigationBarItem(
            icon = { R.drawable.logo},
            label = { Text("Mapa") },
            selected = true, // Aquí pondrías la lógica de cuál está activo
            onClick = { onNavigate("mapa") }
        )

        // Botón 2: Actividades
        NavigationBarItem(
            icon = {  R.drawable.logo },
            label = { Text("Actividades") },
            selected = false,
            onClick = { onNavigate("actividades") }
        )

        // Botón 3: Ajustes
        NavigationBarItem(
            icon = {  R.drawable.logo },
            label = { Text("Ajustes") },
            selected = false,
            onClick = { onNavigate("ajustes") }
        )
    }
}