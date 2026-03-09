package com.example.mapptuu.ui.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private lateinit var processCameraProvider: ProcessCameraProvider
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest
    private val _capturedImage = MutableStateFlow<String?>(null)
    private var cameraSelector: CameraSelector = DEFAULT_BACK_CAMERA

    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageUri: StateFlow<Uri?> = _capturedImageUri.asStateFlow()

    private val previewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.value = newSurfaceRequest
        }
    }

    private val imageCaptureUseCase = ImageCapture.Builder().build()

    suspend fun bindToCamera(lifecycleOwner: LifecycleOwner, )
    {
        processCameraProvider = ProcessCameraProvider.awaitInstance(context)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraSelector,
            previewUseCase,
            imageCaptureUseCase
        )

        try {
            awaitCancellation()
        }
        finally {
            processCameraProvider.unbindAll()
        }

    }

    fun captureImage() {
        val fileName = SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.DISPLAY_NAME, "image_$fileName.jpg")
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCaptureUseCase.takePicture(
            outputOptions,
            context.mainExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = outputFileResults.savedUri
                    _capturedImageUri.value = outputFileResults.savedUri as Uri
                    // aquí ya tienes la foto guardada en galería (uri)
                }

                override fun onError(exception: ImageCaptureException) {
                    // manejar error si quieres (log, estado, toast, etc.)
                }
            }
        )

    }
    fun clearCapturedImage() {
        _capturedImageUri.value = null
    }

    fun switchCamera() {
        if (cameraSelector==DEFAULT_FRONT_CAMERA) {
            cameraSelector = DEFAULT_BACK_CAMERA
        }
        else {
            cameraSelector = DEFAULT_FRONT_CAMERA
        }
    }

    fun unbindCamera(){
        processCameraProvider.unbindAll()
    }

}