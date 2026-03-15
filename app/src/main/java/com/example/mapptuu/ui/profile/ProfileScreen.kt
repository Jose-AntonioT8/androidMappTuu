package com.example.mapptuu.ui.profile


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mapptuu.R
import com.example.mapptuu.ui.component.Header
import com.example.mapptuu.ui.navigation.navigateToCamera
import com.example.mapptuu.ui.navigation.navigateToLandingPage
import android.Manifest.permission.POST_NOTIFICATIONS
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
){
    val context = LocalContext.current

    val showPhotoMenu by viewModel.showPhotoMenu.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()
    val userinfo by viewModel.userinfo.collectAsState()
    val notificationPermissionState = rememberPermissionState(permission = POST_NOTIFICATIONS)

    LaunchedEffect(notificationPermissionState.status) {
        if (!notificationPermissionState.status.isGranted) {
            notificationPermissionState.launchPermissionRequest()
        }
    }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val contentResolver = context.contentResolver

            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            try {
                contentResolver.takePersistableUriPermission(uri, takeFlags)
            } catch (e: Exception) {
                Log.e("Error de permisos en profilescreen", e.toString())
            }

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
            .padding(innerPadding)
            .background(Color(0xFFBFDBFE))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp, bottom = 24.dp),
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
                    contentAlignment = Alignment.Center,
                ) {

                    if (profileImageUri != null) {
                        AsyncImage(
                            model = profileImageUri,
                            contentDescription = stringResource(R.string.profileFoto),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(text = "👤", fontSize = 48.sp)
                    }



                }

                Spacer(modifier = Modifier.size(48.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Column {
                        Text(text = stringResource(R.string.userName),
                            fontSize = 16.sp,
                            color = Color.Black
                            )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = userinfo?.name ?: stringResource(R.string.loading),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(text = stringResource(R.string.email),
                            fontSize = 16.sp,
                            color = Color.Black,

                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = userinfo?.email ?: stringResource(R.string.loading),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(text = stringResource(R.string.accountCreatedAt),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, shape = RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text =java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                                    .format(userinfo?.createdAt?.toDate() ?: java.util.Date()),
                                fontSize = 16.sp,
                                color = Color.Black,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { viewModel.onLogOut()
                        navController.navigateToLandingPage()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444),
                        contentColor = Color.White
                    )

                ) {
                    Text(stringResource(R.string.logout), fontWeight = FontWeight.Bold)
                }

                if (showPhotoMenu) {
                    Popup(
                        alignment = Alignment.TopCenter,
                        onDismissRequest = { viewModel.onDismissPhotoMenu() },
                        properties = PopupProperties(focusable = true),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 76.dp)
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(stringResource(R.string.changeProfilePhoto))
                            Button(onClick = {
                                viewModel.onDismissPhotoMenu()
                                navController.navigateToCamera()
                            }) {
                                Text(stringResource(R.string.fromCamera))
                            }
                            Button(onClick = {
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(stringResource(R.string.fromGalery))
                            }
                        }
                    }
                }
            }
        }
    }
}
