package com.example.mapptuu.ui.component


import androidx.compose.foundation.Image
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.mapptuu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    title: String = "MappTuu",
    onMenuClick: () -> Unit = {},
    onBackClick: (() -> Unit)? = null
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            // Solo mostramos el icono si nos pasan una función para ir atrás
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Image(
                        painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                menuExpanded = true }){
                Image(
                    painterResource(id = R.drawable.menu_burguer),
                    contentDescription = "menu hamburguesa",
                )
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false } // Cerrar si tocas fuera
            ) {
                DropdownMenuItem(
                    text = { Text("Perfil") },
                    onClick = {
                        menuExpanded = false

                    }
                )
                DropdownMenuItem(
                    text = { Text("Ajustes") },
                    onClick = {
                        menuExpanded = false

                    }
                )
                // ... más opciones
            }
        }
    )
}