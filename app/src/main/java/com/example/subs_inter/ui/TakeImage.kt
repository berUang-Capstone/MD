package com.example.subs_inter.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentTakeImageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TakeImage : Fragment() {
    private var binding: FragmentTakeImageBinding? = null

    private var currentPhotoPath: String? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            startTakePhoto()
        } else {
            Toast.makeText(requireContext(), "Permissions not granted by the user.", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            handleCameraPhoto()
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            handleGalleryImage(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTakeImageBinding.inflate(inflater, container, false)
        return binding?.apply {
            buttonCapture.setOnClickListener {
                if (hasPermissions()) {
                    startTakePhoto()
                } else {
                    requestPermissions()
                }
            }

            buttonPickGallery.setOnClickListener {
                pickImageFromGallery()
            }

            buttonSend.setOnClickListener {
                sendImageData()
            }
        }?.root
    }

    private fun hasPermissions(): Boolean = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        permissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createImageFile()?.also { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )
            currentPhotoPath = file.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraLauncher.launch(intent)
        }
    }

    private fun handleCameraPhoto() {
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
        binding?.apply {
            imageStoryUpload.setImageBitmap(bitmap)
            imageStoryUpload.visibility = View.VISIBLE
            buttonSend.visibility = View.VISIBLE
            buttonCapture.visibility = View.GONE
            buttonPickGallery.visibility = View.GONE
        }
    }
    override fun onResume() {
        super.onResume()
        // Hide the bottom navigation bar when this fragment is resumed
        (activity as? MainActivity)?.hideBottomNav(true)
    }
    private fun pickImageFromGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun handleGalleryImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(uri))
        bitmap?.let {
            binding?.apply {
                imageStoryUpload.setImageBitmap(bitmap)
                imageStoryUpload.visibility = View.VISIBLE
                buttonSend.visibility = View.VISIBLE
                buttonCapture.visibility = View.GONE
                buttonPickGallery.visibility = View.GONE
            }
            saveImageToJPEG(bitmap)
        }
    }

    private fun saveImageToJPEG(bitmap: Bitmap) {
        val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile: File = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        currentPhotoPath = imageFile.absolutePath
    }

    private fun sendImageData() {
        Toast.makeText(requireContext(), "Sending data...", Toast.LENGTH_SHORT).show()
        val categoryFragment = WriteDetailFragment()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, categoryFragment, WriteDetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).also {
                // Save a file path for use with ACTION_VIEW intents
                currentPhotoPath = it.absolutePath
            }
        } catch (ex: IOException) {
            // Error occurred while creating the File
            Toast.makeText(requireContext(), "Error saving photo", Toast.LENGTH_SHORT).show()
            null
        }
    }
    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }
}
