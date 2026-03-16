package com.example.mapptuu.ui.camera

import android.Manifest
import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mapptuu.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,

) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val capturedUri by viewModel.capturedImageUri.collectAsStateWithLifecycle()
    if (!cameraPermissionState.status.isGranted) {
        LaunchedEffect("CAMERA_PERMISSION") {
            cameraPermissionState.launchPermissionRequest()
        }
    } else {
        capturedUri?.let { uri ->
            Box(modifier = modifier.fillMaxSize()) {

                AsyncImage(
                    model = uri.toString(),
                    contentDescription = "Foto capturada",
                    modifier = Modifier.fillMaxSize()
                )
                Button(
                    onClick = {
                        viewModel.saveProfilePicture(uri.toString())
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(66.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text("Usar como foto de perfil")
                }
                Button(
                    onClick = { viewModel.clearCapturedImage() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text("Otra foto")
                }
            }
        } ?: CameraPreview(
            modifier = modifier,
            viewModel = viewModel,
        )
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(
            lifecycleOwner = lifecycleOwner,
        )

    }
    val shiftScope = rememberCoroutineScope()

    surfaceRequest?.let { request ->
        Box(modifier = modifier.fillMaxSize()) {
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = Modifier.matchParentSize()
            )
            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                Button(
                    onClick = { viewModel.captureImage() },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.tomarfoto),
                        contentDescription = ""
                    )
                }
                Button(
                    onClick = {
                        shiftScope.launch {
                            viewModel.unbindCamera()
                            viewModel.switchCamera()
                            viewModel.bindToCamera(
                                lifecycleOwner = lifecycleOwner,
                            )
                        }
                              },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.girarcamara),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}