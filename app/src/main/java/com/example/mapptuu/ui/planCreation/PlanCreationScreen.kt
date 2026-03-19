package com.example.mapptuu.ui.planCreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanCreationScreen (
    modifier : Modifier = Modifier,
    viewModel : PlanCreationViewModel = hiltViewModel(),
    onNavegationBack:()->Unit
){
    Scaffold(
        topBar = {
            Header() {  }
        },
    ) { innerPadding ->
    Card(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp).padding(innerPadding)
    ) {
        var expanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                value = viewModel.name,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text(stringResource(R.string.name)) },
                onValueChange = { viewModel.name = it }

            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            ) {
                //actividades para mostrar en el dropdown
                val selectedNames = viewModel.activities
                    .filter { viewModel.selectedActivityIds.contains(it.id) }
                    .map { it.name }
                    .toList()
                //cuando seleccionemos 2 o mas actividades se concatenaran con ,
                val fullText = selectedNames.joinToString(", ")
                //si hay mas de 35 caracteres, se borran tres y se ponen tres puntos, para que quede mejor
                val fieldValue = when {
                    selectedNames.isEmpty() -> ""
                    fullText.length <= 35 -> fullText
                    else -> fullText.take(32) + "…"
                }

                OutlinedTextField(
                    value = fieldValue,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text(stringResource(R.string.name_activities)) },
                    //icono para hacer el dropdown
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        //sin el menuAnchor el desplegable no se desplega
                        .menuAnchor()
                        .fillMaxWidth(),
                )
                //en este caso es de multiple seleccion
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    viewModel.activities
                        .forEach { activity ->
                            val checked = viewModel.selectedActivityIds.contains(activity.id)
                            DropdownMenuItem(
                                text = { Text(activity.name) },
                                leadingIcon = {
                                    Checkbox(
                                        checked = checked,
                                        onCheckedChange = null,
                                    )
                                },
                                onClick = {
                                    viewModel.toggleActivitySelection(activity.id)
                                }
                            )
                        }
                }
            }
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
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,) {
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