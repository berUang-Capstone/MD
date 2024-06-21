package com.example.subs_inter.ui.auth.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.subs_inter.databinding.ActivityRegisterBinding
import com.example.subs_inter.ui.MainActivity
import com.example.subs_inter.ui.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private val viewModel: RegisterViewModel by viewModels()


    override fun onStart() {
        super.onStart()
        viewModel.fetchLoginStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this).apply {
            setTitle("Logging")
            setMessage("Please Wait")
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        binding.registerBtn.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password == confirmPassword) {
                    processRegister(username, email, password)
                } else {
                    Toast.makeText(this, "Passwords do not match", LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun processRegister(fullname: String, email: String, password: String) {
        progressDialog.show()
        viewModel.register(email, password)
        progressDialog.dismiss()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
