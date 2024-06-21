package com.example.subs_inter.ui.auth.login

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.subs_inter.R
import com.example.subs_inter.component.customView.EmailCV
import com.example.subs_inter.component.customView.PasswordCV
import com.example.subs_inter.databinding.ActivityLoginBinding
import com.example.subs_inter.ui.MainActivity
import com.example.subs_inter.ui.auth.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: ProgressDialog
    private val viewModel: LoginViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? PermissionCallback)?.onPermissionsGranted()
        } else {
            Toast.makeText(this, "Camera permission is required to take photos.", Toast.LENGTH_SHORT).show()

        }
    }
    override fun onStart() {
        super.onStart()
        viewModel.fetchLoginStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        handleLoading()

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if ((binding.emailInput as EmailCV).isEmailValid && (binding.passwordInput as PasswordCV).isPasswordValid) {
                processLogin(email, password)
            } else {
                Toast.makeText(this, "Please ensure email and password are valid", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLoading() {
        progressDialog = ProgressDialog(this).apply {
            setTitle("Logging In")
            setMessage("Please wait...")
        }
    }


    private fun processLogin(email: String, password: String) {
        viewModel.login(email, password)

    }

    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            // All permissions are already granted
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? PermissionCallback)?.onPermissionsGranted()
        }
    }
    interface PermissionCallback {
        fun onPermissionsGranted()
    }
}
