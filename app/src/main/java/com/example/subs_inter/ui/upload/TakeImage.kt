package com.example.subs_inter.ui.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.subs_inter.PopUpFragment
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentTakeImageBinding
import com.example.subs_inter.ui.MainActivity
import com.example.subs_inter.ui.add_detail.WriteDetailMoneyFragment
import com.example.subs_inter.ui.summary.FragmentSummary
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TakeImage : Fragment() {
    private var _binding: FragmentTakeImageBinding? = null
    private val binding get() = _binding!!

    private var currentPhotoPath: String? = null
    private var currentPhotoUri: Uri? = null
    private val viewModel: TakeImageViewModel by viewModels()

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            currentPhotoUri = Uri.fromFile(File(currentPhotoPath!!))
            displayImage(currentPhotoUri!!)
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            currentPhotoUri = it
            displayImage(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTakeImageBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.buttonCapture.setOnClickListener { startTakePhoto() }
        binding.buttonPickGallery.setOnClickListener { pickImageFromGallery() }
        binding.buttonSend.setOnClickListener { sendImageData() }



        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
            binding.buttonSend.isEnabled = !isLoading  // Disable the send button when loading
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) navigateToSummary()
        }
    }

    private fun navigateToSummary() {
        binding.buttonSend.isEnabled = false
        val categoryFragment = PopUpFragment()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            add(R.id.nav_host_fragment, categoryFragment, PopUpFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }

    }

    override fun onResume() {
        super.onResume()
        binding.buttonSend.isEnabled = true
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun startTakePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            createImageFile()?.let { file ->
                val photoURI: Uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", file)
                currentPhotoPath = file.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                cameraLauncher.launch(intent)
            }
        }
    }

    private fun pickImageFromGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun displayImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(uri))
        bitmap?.let {
            binding.imageStoryUpload.setImageBitmap(it)
            binding.imageStoryUpload.visibility = View.VISIBLE
            binding.buttonSend.visibility = View.VISIBLE
            binding.buttonCapture.visibility = View.GONE
            binding.buttonPickGallery.visibility = View.GONE
        }
    }

    private var isSendingData = false

    private fun sendImageData() {
        if (!isSendingData) {
            isSendingData = true
            currentPhotoUri?.let {
                viewModel.scanTheImage(it.toString(), requireContext())
                Toast.makeText(requireContext(), "Sending data...", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            isSendingData = false
        } else {
            Toast.makeText(requireContext(), "Already sending data...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createImageFile(): File? {
        return try {
            val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
            val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).also {
                currentPhotoPath = it.absolutePath
            }
        } catch (ex: IOException) {
            Toast.makeText(requireContext(), "Error saving photo", Toast.LENGTH_SHORT).show()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }
}
