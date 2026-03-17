package com.example.mapptuu.ui.activityUpdate

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
import androidx.compose.ui.platform.LocalFocusManager
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
    onNavigateToList:()->Unit
){
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
                value = viewModel.longitude,
                singleLine = false,
                isError = viewModel.isError,
                label = { Text(stringResource(R.string.longitude)) },
                onValueChange = { viewModel.longitude = it }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp).fillMaxWidth(),
                value = viewModel.latitude,
                singleLine = false,
                isError = viewModel.isError,
                label = { Text(stringResource(R.string.latitude)) },
                onValueChange = { viewModel.latitude = it }
            )
            Row(modifier = Modifier.padding(8.dp)) {
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