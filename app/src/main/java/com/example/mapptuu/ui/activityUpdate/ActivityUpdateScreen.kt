package com.example.mapptuu.ui.activityUpdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mapptuu.R
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mapptuu.ui.component.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityUpdateScreen(
    modifier : Modifier = Modifier,
    viewModel : ActivityUpdateViewModel = hiltViewModel(),
    pickedLat: String? = null,
    pickedLng: String? = null,
    onOpenLocationPicker: (currentLat: String, currentLng: String) -> Unit,
    onNavigateToList:()->Unit
){
    //Si se selecciona una latitud y longitud se guarda en el viewModel si no son nulas o no estan vacias, la logica es que si una esta vacia, no son datos validos
    //este launchedeffect sirve para que el if se ejecute solo cuando cambien los valores de pickedLat o pickedLng
    LaunchedEffect(pickedLat, pickedLng) {
        if (!pickedLat.isNullOrBlank() && !pickedLng.isNullOrBlank()) {
            viewModel.latitude = pickedLat
            viewModel.longitude = pickedLng
        }
    }

    LaunchedEffect(viewModel.updateCompleted) {
        viewModel.updateCompleted.collect { done ->
            if (done) {
                viewModel.consumeUpdateCompleted()
                onNavigateToList()
            }
        }
    }
    Scaffold (
        topBar = {
            Header() {  }
        },
    ){ innerPadding ->
    Card(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp).padding(innerPadding)
    ) {
        var expanded by remember { mutableStateOf(false) }
        val activityTypes = viewModel.activityTypes
        val selectedTypeName = activityTypes.firstOrNull { it.id == viewModel.activityTypeId }?.name
            ?: viewModel.activityTypeId

        Column(modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth().padding(start = 8.dp),
                value = viewModel.name,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text(stringResource(R.string.name)) },
                onValueChange = { viewModel.name = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp).fillMaxWidth(),
                value = viewModel.description,
                singleLine = false,
                isError = viewModel.isError,
                label = { Text(stringResource(R.string.description)) },
                onValueChange = { viewModel.description = it }
            )
            //lista en forma desplegable para elegir un tipo de actividad

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = selectedTypeName,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text(stringResource(R.string.activity_type_id)) },
                    //icono para hacer el dropdown
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        //sin el menuAnchor el desplegable no se desplega
                        .menuAnchor()
                        .fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    //el texto que muestra es el nombre del tipo de actividad y guarda el id para enviarlo al remote
                    activityTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = {
                                viewModel.activityTypeId = type.id
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp).fillMaxWidth(),
                //campo de texto que muestra el valor de la ubicacion, si hemos elegido alguna ubicacion, muestra su latitud y longitud, si no, no
                value = if (viewModel.latitude.isNotBlank() && viewModel.longitude.isNotBlank()) {
                    "${viewModel.latitude}, ${viewModel.longitude}"
                } else {
                    ""
                },
                singleLine = true,
                isError = viewModel.isError,
                readOnly = true,
                label = { Text(stringResource(R.string.ubication)+" (lat, lng)") },
                onValueChange = { }
            )
            Button(
                modifier = Modifier
                    .padding(start = 8.dp).fillMaxWidth(),
                onClick = {
                    //se llama a la pantalla para elegir ubicacion y le pasamos los valores actuales de longitud y latitud del viewmodel que se enviará para actualizar la actividad
                    onOpenLocationPicker(viewModel.latitude, viewModel.longitude)
                },
            ) {
                Text(stringResource(R.string.selectInMap))
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,) {
                Button(
                    onClick = {
                        viewModel.update()
                    },
                ) {
                    Text(stringResource(R.string.update))
                }
                Button(
                    onClick = {
                        onNavigateToList()
                    },
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }

    }
}