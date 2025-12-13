package com.example.mapptuu.ui.landingPage

import androidx.compose.foundation.layout.*import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun LandingPageScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToActivities: () -> Unit,
    onNavigateToPlans: () -> Unit,
    viewModel: LandingPageViewModel = hiltViewModel()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Aumentamos el padding para más aire
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la aplicación con más énfasis
            Text(
                text = "MappTuu",
                style = MaterialTheme.typography.displaySmall, // Estilo más grande
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu compañero de aventuras",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Un color más suave
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(64.dp)) // Más espacio para separar el título

            // Lógica para mostrar contenido según el estado de sesión
            if (viewModel.isLogged) {
                LoggedInContent(
                    onNavigateToActivities = onNavigateToActivities,
                    onNavigateToPlans = onNavigateToPlans,
                    onLogOut = { viewModel.onLogOut() }
                )
            } else {
                LoggedOutContent(
                    onNavigateToLogin = onNavigateToLogin,
                    onNavigateToRegister = onNavigateToRegister
                )
            }
        }
    }
}

@Composable
private fun LoggedInContent(
    onNavigateToActivities: () -> Unit,
    onNavigateToPlans: () -> Unit,
    onLogOut: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio automático entre elementos
    ) {
        OptionCard(
            title = "Explorar Actividades",
            description = "Descubre lugares y experiencias únicas.",
            onClick = onNavigateToActivities
        )
        OptionCard(
            title = "Mis Planes",
            description = "Organiza y gestiona tus próximas aventuras.",
            onClick = onNavigateToPlans
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botón de cierre de sesión
        Button(
            onClick = onLogOut,
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary, // Un color diferente
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun LoggedOutContent(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Comienza tu viaje",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Botón de acción principal
        Button(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar Sesión", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Botón de acción secundaria
        OutlinedButton(
            onClick = onNavigateToRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Crear una cuenta")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OptionCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = MaterialTheme.shapes.large, // Bordes más redondeados
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
