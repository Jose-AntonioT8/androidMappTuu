package com.example.mapptuu.ui.planUpdate


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun PlanUpdateScreen(
    modifier : Modifier = Modifier,
    viewModel : PlanUpdateViewModel = hiltViewModel(),
    onNavigateToDetails:(String)->Unit
){
    Scaffold(
        topBar = {
            Header() {  }
        },
    ) { innerPadding ->
    Card(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp).padding(innerPadding)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
                value = viewModel.activitiesIdsInput,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Id de las actividades: ") },
                onValueChange = { viewModel.activitiesIdsInput = it }

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Público: ",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = viewModel.visibility,
                    onCheckedChange = { viewModel.visibility = it }
                )
            }
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
            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                        viewModel.update()
                        onNavigateToDetails(viewModel.planId)
                    },
                ) {
                    Text("Actualizar")
                }
                Button(
                    onClick = {
                        onNavigateToDetails(viewModel.planId)
                    },
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
    }
}