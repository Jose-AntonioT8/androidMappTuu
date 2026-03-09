package com.example.mapptuu.ui.landingPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mapptuu.R

@Composable
fun LandingPageScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToActivities: () -> Unit,
    onNavigateToPlans: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: LandingPageViewModel = hiltViewModel()
) {
    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondorealista),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop

        )


        Surface(
            modifier = modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la aplicación
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(64.dp))


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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Logo arriba a la izquierda
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.size(50.dp)
            )
            IconButton(
                onClick = {
                    if (viewModel.isLogged) {
                        onNavigateToProfile()
                    } else {
                        onNavigateToLogin()
                    }
                },
                modifier = Modifier.size(37.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.perso),
                    contentDescription = stringResource(R.string.profile_desc),
                    modifier = Modifier.fillMaxSize()
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OptionCard(
            title = stringResource(R.string.explore_activities),
            description = stringResource(R.string.expri),
            onClick = onNavigateToActivities
        )
        OptionCard(
            title = stringResource(R.string.my_plans),
            description = stringResource(R.string.org),
            onClick = onNavigateToPlans
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Botón de cierre de sesión
        Button(
            onClick = onLogOut,
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(stringResource(R.string.logout), fontWeight = FontWeight.Bold)
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
            text = stringResource(R.string.journey),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(stringResource(R.string.login), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onNavigateToRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(stringResource(R.string.create_account))
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
