package com.example.mapptuu.ui.activityList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Footer
import com.example.mapptuu.ui.component.Header

@Composable
fun ActivityListScreen(
    onCreate: () -> Unit,
    onShowDetail: (String) -> Unit,
    onPlanList: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ActivityListViewModel = hiltViewModel(),
    onNavigateToMap: () -> Unit,
    onNavigateToSetting: () -> Unit,
    navController: NavController,
    onNavigateToLanding: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()


    Scaffold(

        topBar = {
            Header(onMenuClick = onNavigateToLanding) {  }
        },
        bottomBar = {
            Footer(
                activeRoute = "lista",
                onNavigate = { route ->
                    when (route) {
                        "mapa" -> onNavigateToMap()
                        "plans" -> onPlanList()
                        "profile" -> onNavigateToProfile()
                    }
                },
                navController = navController
            )
        }
    ) { innerPadding ->


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top =0.dp, bottom = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.activities),
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                viewModel = viewModel,
                onPlanList = onPlanList,
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
                    ActivityList(
                        activities = currentState.activities,
                        onShowDetail = onShowDetail
                    )
                }
            }
        }
    }
}


@Composable
private fun SearchBar(
    viewModel: ActivityListViewModel,
    onPlanList:()->Unit,
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
                        contentDescription = stringResource(R.string.search),
                        Modifier.size(18.dp)
                    )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onCreate
            ) {
                Text(stringResource(R.string.create))
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
private fun ActivityList(
    activities: List<ListItemUiState>,
    onShowDetail: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = activities,
            key = { activity -> activity.id }
        ) { activity ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowDetail(activity.id) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = activity.image,
                        contentDescription = activity.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = activity.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = Color(0xFFFFB300).copy(alpha = 0.1f),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "${activity.rating} ESTRELLAS",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFE65100)
                                )
                            }

                            Text(
                                text = "#${activity.id.take(5)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }


                }
            }
        }
    }
}
