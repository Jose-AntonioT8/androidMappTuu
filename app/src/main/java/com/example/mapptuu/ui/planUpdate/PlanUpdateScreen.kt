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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun PlanUpdateScreen(
    modifier : Modifier = Modifier,
    viewModel : PlanUpdateViewModel = hiltViewModel(),
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
    Scaffold(
        topBar = {
            Header() {  }
        },
    ) { innerPadding ->
    Card(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp).padding(innerPadding)
    ) {
        val focusManager = LocalFocusManager.current
        var expanded by remember { mutableStateOf(false) }

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
                label = { Text(stringResource(R.string.name),) },
                onValueChange = { viewModel.name = it }

            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            ) {
                val selectedCount = viewModel.selectedActivityIds.size
                val fieldValue = if (selectedCount == 0) {
                    ""
                } else {
                    stringResource(R.string.name_activities) + ": " + selectedCount
                }

                OutlinedTextField(
                    value = fieldValue,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    isError = viewModel.isError,
                    label = { Text(stringResource(R.string.name_activities)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    viewModel.activities
                        .sortedBy { it.name.lowercase() }
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
                                    focusManager.clearFocus()
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
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
                    text = stringResource(R.string.is_public)+":",
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
                label = { Text(stringResource(R.string.description)) },
                onValueChange = { viewModel.description = it }

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