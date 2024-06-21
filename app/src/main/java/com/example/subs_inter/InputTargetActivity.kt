package com.example.subs_inter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InputTargetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_target)

        val priceEditText = findViewById<EditText>(R.id.etPrice)
        val submitButton = findViewById<Button>(R.id.btnSubmitPrice)

        submitButton.setOnClickListener {
            val priceText = priceEditText.text.toString()
            if (priceText.isEmpty()) {
                Toast.makeText(this, "Please enter a price", Toast.LENGTH_SHORT).show()
            } else {
                val price = priceText.toDouble()
                // Save the price using SharedPreferences
                val sharedPref = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putFloat("targetPrice", price.toFloat())
                    apply()
                }
                Toast.makeText(this, "Price entered: $$price", Toast.LENGTH_LONG).show()
                finish()  // Close this activity after saving the price
            }
        }
    }
}