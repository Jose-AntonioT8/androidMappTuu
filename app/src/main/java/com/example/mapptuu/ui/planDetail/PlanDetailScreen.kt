package com.example.mapptuu.ui.planDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PlanDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ActivityDetailViewModel = hiltViewModel(),
    onNavegationBack: () -> Unit,
    onUpdatePlan: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { Header() { } },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            PlanDetailContent(
                modifier = Modifier.weight(1f),
                name = uiState.name,
                id = uiState.id,
                imageRef = uiState.imageRef,
                createdAt = uiState.createdAt,
                description = uiState.description,
                ownerId = uiState.ownerId,
                visibility = uiState.visibility,
                rating = uiState.rating,
                activityIds = uiState.activityIds,
            )/*
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
            { Text(stringResource(R.string.delete_plan)) }*/
            Button(
                onClick = { onNavegationBack() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.back))
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun PlanDetailContent(
    modifier: Modifier,

    id:String,
    activityIds:List<String>?,
    createdAt: Timestamp,
    description: String,
    imageRef: String,
    name: String,
    ownerId: String,
    rating: Float,
    visibility: Boolean
) {
    val dateString = remember(createdAt) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(createdAt.toDate())
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
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

                StatusBadge(
                    text = if (visibility) "PÚBLICO" else "PRIVADO",
                    containerColor = if (visibility) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    contentColor = if (visibility) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = imageRef,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 120.dp, height = 180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    InfoRow(label = "PUNTUACIÓN", value = "$rating / 5.0")
                    InfoRow(label = "FECHA", value = dateString)
                    InfoRow(label = "AUTOR", value = ownerId.take(10))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ACTIVIDADES",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                activityIds?.forEach { actId ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = actId,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "SOBRE ESTE PLAN",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun StatusBadge(text: String, containerColor: Color, contentColor: Color) {
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
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