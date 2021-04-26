package com.example.cameraxapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.display.DisplayManager
import android.media.MediaActionSound
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.cameraxapp.R
import com.example.cameraxapp.databinding.FragmentCameraBinding
import com.example.cameraxapp.model.PictureModel
import com.example.cameraxapp.util.Constants
import com.example.cameraxapp.util.Constants.Companion.ANIMATION_FAST_MILLIS
import com.example.cameraxapp.util.Constants.Companion.ANIMATION_SLOW_MILLIS
import com.example.cameraxapp.util.Constants.Companion.TAG
import com.example.cameraxapp.util.PermissionCheck
import com.example.cameraxapp.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.coroutines.*
import java.io.File
import java.lang.Runnable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CameraFragment : Fragment() {
    private var cameraCheck = false
    private var imageCapture: ImageCapture? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var displayId: Int = -1
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private val pictureViewModel: PictureViewModel by activityViewModels()

    private lateinit var permissionCheck: PermissionCheck
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var broadcastManager: LocalBroadcastManager
    private lateinit var viewFinder: PreviewView

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@CameraFragment.displayId) {
                Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val FILENAME_FORMAT = "roundId_index"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }

        permissionCheck =
            PermissionCheck(this@CameraFragment, object : PermissionCheck.PermissionListener {
                override fun permissionAllowed() {
                    cameraCheck = true
                    binding.cameraView.visibility = View.VISIBLE
                }
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinder = binding.previewView

        pictureViewModel.getAllPictureData().observe(viewLifecycleOwner) {

        }

        // 백그라운드 준비
        cameraExecutor = Executors.newSingleThreadExecutor()
        broadcastManager = LocalBroadcastManager.getInstance(requireContext())

        displayManager.registerDisplayListener(displayListener, null)

        viewFinder.post {
            displayId = viewFinder.display.displayId
            permissionCheck.hasPermissions(arrayListOf(Constants.PERMISSION_CAMERA))

            if (cameraCheck) {
                startCamera()
            }
        }


        // 사진찍기
        binding.cameraCaptureButton.setOnClickListener {
            takePhoto()
            // 카메라 셔터 소리
            val cameraSound = MediaActionSound()
//            cameraSound.play(MediaActionSound.SHUTTER_CLICK)
        }

        // 앨범으로 이동
        binding.photoViewButton.setOnClickListener {
            // 성공
//            setResult(ALBUM_OK)
//            finish()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, AlbumFragment()).commit()
        }
        // 카메라 회전
        binding.cameraSwitchButton.setOnClickListener {
            lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }
            startCamera()
        }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        // 캡쳐 이미지가 null 이면 함수 종료
        val imageCapture = imageCapture ?: return

        // 이미지 저장할 파일 만듬, 파일이름이 고유하도록 타임스탬프추가
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(DATE_FORMAT, Locale.KOREA).format(System.currentTimeMillis())
                    + "_$FILENAME_FORMAT.jpg"
        )

        val metadata = ImageCapture.Metadata().apply {

            // Mirror image when using the front camera
            isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
        }

        // 파일 만들기 + MataData
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .setMetadata(metadata)
            .build()

        // 이미지 캡처에 전달, 오류, 저장 콜백 지정
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                // 이미지 저장 위치
                // Adnroid/data/앱패키지명/cache
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    Log.d(TAG, "Photo succeeded: $savedUri")

                    lifecycleScope.launch {
                        pictureViewModel.setTakePhoto(
                            arrayListOf(
                                PictureModel(
                                    savedUri.toString(),
                                    SimpleDateFormat(DATE_FORMAT, Locale.KOREA).format(System.currentTimeMillis()),
                                    0,
                                    photoFile
                                )
                            )
                        )
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment, PictureFragment()).commit()
                    }

                    val mimeType = MimeTypeMap.getSingleton()
                        .getMimeTypeFromExtension(savedUri.toFile().extension)

                    MediaScannerConnection.scanFile(
                        context,
                        arrayOf(savedUri.toFile().absolutePath),
                        arrayOf(mimeType)
                    ) { path, uri ->
                        Log.d(TAG, "Image capture scanned into media store: $uri, $path")
                    }

                }
            })

        // API 23+ 사진찍을 때 화면 깜빡임
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Display flash animation to indicate that photo was captured
            viewFinder.postDelayed({
                viewFinder.foreground = ColorDrawable(Color.WHITE)
                viewFinder.postDelayed(
                    { viewFinder.foreground = null }, ANIMATION_FAST_MILLIS
                )
            }, ANIMATION_SLOW_MILLIS)
        }
    }

    // 비율 설정
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun startCamera() {
        // 카메라 라이프사이클 등록
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = viewFinder.display.rotation

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // 뷰 파인더에서 공급자를 가져온 다음 미리보기 설정
            val preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }

            // 캡처 이미지 추가(토스트)
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()

            // 카메라 기본 설정
            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

            // Unbind use cases before rebinding
            cameraProvider.unbindAll()

            try {
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }


        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
        cameraExecutor.shutdown()
        displayManager.unregisterDisplayListener(displayListener)
    }

    private fun getOutputDirectory(): File {
        // 앱 내부 캐시 폴더에 저장
        val mediaDir = requireActivity().externalCacheDirs.firstOrNull()?.let {
            File(it, "").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionCheck.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "Camera: $requestCode, $resultCode, $data")
    }
}