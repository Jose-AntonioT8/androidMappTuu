package com.example.mapptuu.ui.activityCreation



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mapptuu.ui.component.Header

@Composable
fun ActivityCreationScreen (
    modifier : Modifier = Modifier,
    viewModel : ActivityCreationViewModel = hiltViewModel(),
    onNavegationBack:()->Unit
){

    Scaffold(
        topBar = {
            Header() {  }
        },
    ) { innerPadding ->
        Card(
            modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp)
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    value = viewModel.name,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text("Nombre") },
                    onValueChange = { viewModel.name = it }

                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    value = viewModel.activityTypeId,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text("Id del tipo de la actividad:") },
                    onValueChange = { viewModel.activityTypeId = it }

                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    value = viewModel.longitude,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text("Longitud: ") },
                    onValueChange = { viewModel.longitude = it }

                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    value = viewModel.latitude,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text("Latitud: ") },
                    onValueChange = { viewModel.latitude = it }

                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(),
                    value = viewModel.description,
                    singleLine = false,
                    isError = viewModel.isError,
                    label = { Text("Descripcion") },
                    onValueChange = { viewModel.description = it }

                )
            }
            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                        viewModel.create()
                        onNavegationBack()
                    },
                ) {
                    Text("Crear")
                }
                Button(
                    onClick = {
                        onNavegationBack()
                    },
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}