package com.example.mapptuu.ui.profile


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.viewfinder.core.impl.ContentScale
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mapptuu.ui.component.Header
import com.example.mapptuu.ui.navigation.navigateToCamera

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),

    modifier: Modifier = Modifier,
){
    val showPhotoMenu by viewModel.showPhotoMenu.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()




    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onImageSelected(uri)
        }
    }

    Scaffold (
        topBar = {
            Header(onBackClick = { navController.popBackStack() })
        },
    ){ innerPadding ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(
                            color = Color(0xFF60A5FA),
                            shape = CircleShape
                        )
                        .clickable {
                            viewModel.onTogglePhotoMenu()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null) {
                        AsyncImage(
                            model = profileImageUri,
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        Text(text = "👤", fontSize = 48.sp)
                    }
                }
                if (showPhotoMenu) {
                    Popup(
                        alignment = Alignment.TopCenter,
                        onDismissRequest = { viewModel.onDismissPhotoMenu() },
                        properties = PopupProperties(focusable = true)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 76.dp)
                                .background(
                                    Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Cambiar foto de perfil:")
                            Button(onClick = {
                                viewModel.onDismissPhotoMenu()
                                navController.navigateToCamera()
                            }) {
                                Text("Desde cámara")
                            }
                            Button(onClick = {
                                galleryLauncher.launch("image/*")
                            }) {
                                Text("Desde galería")
                            }
                        }
                    }
                }
            }
        }
    }
}
