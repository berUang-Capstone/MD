package com.example.subs_inter.ui.dashboard.by_type

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.subs_inter.R
import com.example.subs_inter.databinding.ActivitySearchByTypeBinding
import com.example.subs_inter.ui.dashboard.by_type.ResultSearchByType.Companion.TYPE_SEARCH

class SearchByType : AppCompatActivity() {
    private lateinit var binding: ActivitySearchByTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the binding
        binding = ActivitySearchByTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Set the content view to binding's root

        setupInitialSpinner()

        // Set up button click listener
        binding.buttonGetAll.setOnClickListener {
            // Get the selected item from the Spinner
            val selectedItem = binding.categorySpinner.selectedItem.toString()

            // Create an Intent to start ResultSearchByType Activity
            val intent = Intent(this, ResultSearchByType::class.java)
            // Pass the selected item as an extra
            intent.putExtra(TYPE_SEARCH, selectedItem)
            startActivity(intent)  // Start the activity with the intent
        }
    }

    private fun setupInitialSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
        }
    }
}
