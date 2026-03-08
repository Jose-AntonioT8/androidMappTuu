package com.example.mapptuu.ui.planList


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Footer
import com.example.mapptuu.ui.component.Header

@Composable
fun PlanListScreen (
    onCreate:()->Unit,
    onShowDetail: (String) -> Unit,
    onNavigateToSetting:() -> Unit,
    onNavigateToMap:() -> Unit,
    onNavigateActivityList:() -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlanListViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            Header() {  }
        },
        bottomBar = {
            Footer(
                activeRoute = "plans",
                onNavigate = { route ->
                    when (route) {
                        "mapa" -> onNavigateToMap()
                        "ajustes" -> onNavigateToSetting()
                    }

                },
                navController = navController
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(innerPadding)
        ) {
            SearchBar(
                viewModel = viewModel,
                onActivityList = onNavigateActivityList,
                onCreate = onCreate,
                isError = uiState is ListUiState.Error,

                )

            when (val currentState = uiState) {
                is ListUiState.Initial -> {
                }

                is ListUiState.Loading -> {
                    ListLoading()
                }

                is ListUiState.Error -> {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                is ListUiState.Succes -> {
                    PlanList(
                        plans = currentState.plans,
                        onShowDetail = onShowDetail
                    )
                }
            }
        }
    }
}


@Composable
private fun SearchBar(
    viewModel: PlanListViewModel,
    onActivityList:()->Unit,
    onCreate:()->Unit,
    isError: Boolean)
{
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            OutlinedTextField(
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 8.dp),
                value = viewModel.busquedaParametros,
                onValueChange = { nuevoTexto ->
                    viewModel.onBusquedaChanged(nuevoTexto)
                },
                singleLine = true,
                isError = isError,
                label = { Text(stringResource(R.string.search)) }
            )
            Button(
                modifier = Modifier.padding(start = 8.dp),
                onClick = {
                    viewModel.search()
                }
            ) {

                Image(
                    painter = painterResource(id = R.drawable.lupa),
                    contentDescription = stringResource(R.string.plan),
                    Modifier.size(18.dp)
                )
            }
            Button(
                modifier = Modifier.padding(start = 8.dp),
                onClick = onCreate
            ) {
                Text(stringResource(R.string.create_plan))
            }


        }
    }

}


@Composable
private fun ListLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    }
}


@Composable
private fun PlanList(
    plans: List<ListItemUiState>,
    onShowDetail: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = plans,
            key = { plan -> plan.id }
        ) { plan ->
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onShowDetail(plan.id) },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        contentDescription = plan.name,
                        model = plan.image,
                        modifier = Modifier.size(60.dp)
                    )
                    Column {
                        Text(text =plan.name)
                        Row {
                            Text("${plan.rating}★")
                        }
                    }
                }
            }
        }
    }
}

