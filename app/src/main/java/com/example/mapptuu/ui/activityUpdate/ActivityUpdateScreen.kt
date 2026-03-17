package com.example.mapptuu.ui.activityUpdate

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mapptuu.R
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mapptuu.ui.component.Header

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
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp).fillMaxWidth(),
                value = viewModel.activityTypeId,
                singleLine = false,
                isError = viewModel.isError,
                label = { Text(stringResource(R.string.activity_type_id)) },
                onValueChange = { viewModel.activityTypeId = it }
            )
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