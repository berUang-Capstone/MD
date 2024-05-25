package com.example.subs_inter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.subs_inter.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var btnlogout: Button

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize the btnlogout button
        btnlogout = findViewById(R.id.logout_btn)

        btnlogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
