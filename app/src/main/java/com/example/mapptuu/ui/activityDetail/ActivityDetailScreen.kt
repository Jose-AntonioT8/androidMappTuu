package com.example.mapptuu.ui.activityDetail



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mapptuu.R
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import kotlin.Unit

@Composable
fun ActivityDetailScreen(
    modifier : Modifier = Modifier,
    viewModel : ActivityDetailViewModel = hiltViewModel(),
    onNavegationBack:()->Unit,
    onUpdateActivity: (String) -> Unit,


    ){
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold() {innerPading ->
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .padding(innerPading)) {
        ActivityDetailScreen(

            modifier = modifier.weight(1f),
            name = uiState.name,
            id = uiState.id,
            imageRef = uiState.imageRef,
            activityTypeId = uiState.activityTypeId,
            createdAt = uiState.createdAt,
            description = uiState.description,
            latitude = uiState.latitude,
            longitude = uiState.longitude,
            ownerId = uiState.ownerId,
            rating = uiState.rating,
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            onUpdateActivity(uiState.id)
        }
        )
        { Text(stringResource(R.string.modify_activity)) }
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            scope.launch {
                try {
                    viewModel.delete(uiState.id)
                    onNavegationBack()
                } catch (e: Exception) {
                    android.util.Log.e("ActivityDetail", "Error al borrar: ${e.message}", e)
                    // Opción: mostrar mensaje al usuario o igualmente volver atrás
                    onNavegationBack()
                }
            }
        }
        )
        { Text(stringResource(R.string.delete_activity)) }
        Button(
            onClick = {
                onNavegationBack()
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(stringResource(R.string.back))
        }
    }
    }


}


@Composable
fun ActivityDetailScreen(
    modifier: Modifier,

    id:String,
    activityTypeId:String,
    createdAt: Timestamp,
    description:String,
    imageRef:String,
    latitude:String,
    longitude:String,
    name:String,
    ownerId:String,
    rating:Float
){
    Card(
        modifier = modifier
            .fillMaxWidth()

            .padding(8.dp)
    ){
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())){
            Text(text = "${stringResource(R.string.id)} $id", Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text= name, Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Row{
                AsyncImage(
                    model = imageRef,
                    contentDescription = name,
                    modifier = Modifier
                        .size(width = 220.dp, height = 340.dp)
                )
                Column {
                    Text(text = "${stringResource(R.string.activity_type_id)} $activityTypeId")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${stringResource(R.string.latitude)} $latitude")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${stringResource(R.string.longitude)} $longitude")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${stringResource(R.string.owner)} $ownerId")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${stringResource(R.string.rating)}: $rating")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${stringResource(R.string.created_at)} $createdAt")
                }
                }

            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text= description, Modifier.padding(start = 10.dp, end = 10.dp))

        }
    }


