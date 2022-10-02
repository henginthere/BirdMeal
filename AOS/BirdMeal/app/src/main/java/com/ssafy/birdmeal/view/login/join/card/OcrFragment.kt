package com.ssafy.birdmeal.view.login.join.card

import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentOcrBinding
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.view.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class OcrFragment : BaseFragment<FragmentOcrBinding>(R.layout.fragment_ocr) {

    private val loginViewModel by activityViewModels<LoginViewModel>()

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraSelector: CameraSelector
    private lateinit var preview: Preview
    private lateinit var takePicture: ImageCapture
    private val analysis by lazy { ImageAnalysis.Builder().build() }
    private val analyzer by lazy { MyImageAnalyzer() }
    private val textRecognizer by lazy {
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
    }

    override fun init() {
        startCamera()
    }

    private fun getPreview(): Preview {
        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(binding.preview.surfaceProvider)
        return preview
    }

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()


            preview = getPreview()
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview)

            takePicture = buildTakePicture()
            cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, takePicture)

            startCapture()
//            startAnalyze()
//            cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, analysis)

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun buildTakePicture(): ImageCapture = ImageCapture.Builder()
        .setTargetRotation(binding.preview.display.rotation)
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build()

    suspend fun ImageCapture.takePicture(executor: Executor): ImageProxy {
        return suspendCoroutine { continuation ->

            takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {

                override fun onCaptureSuccess(image: ImageProxy) {
                    continuation.resume(image)
                    super.onCaptureSuccess(image)
                }

                override fun onError(exception: ImageCaptureException) {
                    continuation.resumeWithException(exception)
                    super.onError(exception)
                }
            })
        }
    }

    private fun startCapture() {
        lifecycle.coroutineScope.launchWhenResumed {
            delay(500)
            val imageProxy =
                takePicture.takePicture(ContextCompat.getMainExecutor(requireContext()))
            analyzer.analyze(imageProxy)
        }
    }

    private fun startAnalyze() {
        analysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()), analyzer)
    }

    inner class MyImageAnalyzer : ImageAnalysis.Analyzer {

        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                // Pass image to an ML Kit Vision API
                var flag = false;
                var text = "";

                val result = textRecognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        // Task completed successfully

                        visionText.textBlocks.forEach { block ->
                            Log.d(TAG, "analyze: ${block.text}")
                            Log.d(TAG, "analyze: ${block.text.length}")
                            if (block.text.length == 19) {
                                block.lines.forEach {
                                    if (it.text.replace(" ", "")
                                            .matches(Regex("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})\$"))
                                    ) {
                                        flag = true
                                        text = it.text
                                        Log.d(TAG, "analyze flag: ${it.text}")
                                    }
                                }
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        Log.d(TAG, "analyze failed: $e")
                    }
                    .addOnCompleteListener {
                        Log.d(TAG, "analyze: $flag")
                        if (!flag) {
                            imageProxy.close()
                            startCapture()
                        } else {
                            loginViewModel.ocr(text)
                        }
                    }
            }
        }
    }
}
