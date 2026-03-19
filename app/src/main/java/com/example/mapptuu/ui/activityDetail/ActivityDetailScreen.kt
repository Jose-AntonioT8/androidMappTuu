package com.example.mapptuu.ui.activityDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mapptuu.R
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlin.Unit
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ActivityDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivityDetailViewModel = hiltViewModel(),
    onNavegationBack: () -> Unit,
    onUpdateActivity: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ActivityDetailContent(
                modifier = Modifier.weight(1f),
                name = uiState.name,
                id = uiState.id,
                imageRef = uiState.imageRef,
                activityTypeName = uiState.activityTypeName,
                createdAt = uiState.createdAt,
                description = uiState.description,
                latitude = uiState.latitude,
                longitude = uiState.longitude,
                ownerId = uiState.ownerId,
                rating = uiState.rating,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isOwner) {
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
                            onNavegationBack()
                        }
                    }
                }
                )
                { Text(stringResource(R.string.delete_activity)) }

            }

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
fun ActivityDetailContent(
    modifier: Modifier,
    id: String,
    activityTypeName: String,
    createdAt: Timestamp,
    description: String,
    imageRef: String,
    latitude: String,
    longitude: String,
    name: String,
    ownerId: String,
    rating: Float
) {
    val dateString = remember(createdAt) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(createdAt.toDate())
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "ID: ${id.take(8)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                    )
                }

                ActivityTypeBadge(text = activityTypeName.uppercase())
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = imageRef,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 130.dp, height = 190.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    InfoItem(label = "PUNTUACIÓN", value = "$rating / 5.0")
                    InfoItem(label = "FECHA", value = dateString)
                    InfoItem(label = "UBICACIÓN", value = "${latitude.take(6)}, ${longitude.take(6)}")
                    InfoItem(label = "AUTOR", value = ownerId.take(10))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DESCRIPCIÓN DE LA ACTIVIDAD",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun ActivityTypeBadge(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
