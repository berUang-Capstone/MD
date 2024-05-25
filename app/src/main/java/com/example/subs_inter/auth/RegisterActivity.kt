package com.example.subs_inter.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.subs_inter.MainActivity
import com.example.subs_inter.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest


class RegisterActivity : AppCompatActivity() {
    lateinit var editusername: EditText
    lateinit var editemail: EditText
    lateinit var editpass: EditText
    lateinit var editpasscom: EditText
    lateinit var btnRegister: Button
    lateinit var btnlogin: Button
    lateinit var progressDialog : ProgressDialog
    var firebaseAuth = FirebaseAuth.getInstance()
    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editusername = findViewById(R.id.username)
        editemail = findViewById(R.id.email)
        editpass = findViewById(R.id.password)
        editpasscom = findViewById(R.id.repassword)
        btnRegister = findViewById(R.id.signupbtn)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("silahkan tunggu")


        btnRegister.setOnClickListener {
            if (editusername.text.isNotEmpty() && editemail.text.isNotEmpty() && editpass.text.isNotEmpty()) {
                if (editpass.text.toString() == editpasscom.text.toString()) {
                    processRegister()
                }
                else{
                    Toast.makeText(this, "silahkan isi dulu semua data", LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "silahkan isi dulu semua data", LENGTH_SHORT).show()
            }

        }
    }
    private fun processRegister(){
        val fullname= editusername.text.toString()
        val email = editemail.text.toString()
        val password = editpass.text.toString()
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task->
                if(task.isSuccessful){
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = fullname
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        .addOnFailureListener{error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()
                        }

                }
                else{
                    progressDialog.dismiss()
                }

            }
            .addOnFailureListener{ error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }

    }
}