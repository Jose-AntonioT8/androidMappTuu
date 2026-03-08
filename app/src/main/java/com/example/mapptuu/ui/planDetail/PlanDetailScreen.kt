package com.example.mapptuu.ui.planDetail

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header
import com.google.firebase.Timestamp
import kotlin.Unit

@Composable
fun PlanDetailScreen(
    modifier : Modifier = Modifier,
    viewModel : ActivityDetailViewModel = hiltViewModel(),
    onNavegationBack:()->Unit,
    onUpdatePlan: (String) -> Unit,

    ){
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Header() {  }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(innerPadding)
        ) {
            PlanDetailScreen(

                modifier = modifier.weight(1f),
                name = uiState.name,
                id = uiState.id,
                imageRef = uiState.imageRef,
                createdAt = uiState.createdAt,
                description = uiState.description,
                ownerId = uiState.ownerId,
                visibility = uiState.visibility,
                rating = uiState.rating,
                activityIds = uiState.activityIds,
            )
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                onUpdatePlan(uiState.id)
            }
            )
            { Text(stringResource(R.string.modify_plan)) }
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                viewModel.delete(uiState.id)
                onNavegationBack()
            }
            )
            { Text(stringResource(R.string.delete_plan)) }
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
fun PlanDetailScreen(
    modifier: Modifier,

    id:String,
    activityIds:List<String>,
    createdAt: Timestamp,
    description:String,
    imageRef:String,
    name:String,
    ownerId:String,
    rating: Float,
    visibility:Boolean
){
    Card(
        modifier = modifier
            .fillMaxWidth()

            .padding(8.dp)
    ){
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())){
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
                    Text(stringResource(R.string.activities))
                    Spacer(modifier = Modifier.height(8.dp))
                    for (id in activityIds) {
                        Text(text = id)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (visibility){
                        Text(stringResource(R.string.is_public))
                    }else{
                        Text(stringResource(R.string.is_private))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "$rating ★")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(R.string.created_at)+": ${createdAt.toDate()}")
                }
            }

        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text= description, Modifier.padding(start = 10.dp, end = 10.dp))

    }
}


