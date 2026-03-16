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
import androidx.compose.ui.res.stringResource
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
            if (onBackClick != null) {
                IconButton(onClick = onMenuClick) {
                    Image(
                        painterResource(id = R.drawable.logo),
                        contentDescription = stringResource(R.string.logo),
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                menuExpanded = true }){
                Image(
                    painterResource(id = R.drawable.menu_burguer),
                    contentDescription = stringResource(R.string.menu),
                )
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.profile)) },
                    onClick = {
                        menuExpanded = false

                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.settings)) },
                    onClick = {
                        menuExpanded = false

                    }
                )

            }
        }
    )
}