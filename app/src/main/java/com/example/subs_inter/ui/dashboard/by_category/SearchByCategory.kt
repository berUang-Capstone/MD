package com.example.subs_inter.ui.dashboard.by_category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.subs_inter.R
import com.example.subs_inter.databinding.ActivitySearchByCategoryBinding
import com.example.subs_inter.ui.dashboard.by_category.ResultSearchByCategory.Companion.CATEGORY_SEARCH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchByCategory : AppCompatActivity() {

    private lateinit var binding: ActivitySearchByCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInitialSpinner()
        setupRadioGroup()
        setupSpinnerInteraction()

        binding.buttonNext.setOnClickListener {
            val selectedCategory = binding.categorySpinner.selectedItem.toString()
            val intent = Intent(this@SearchByCategory, ResultSearchByCategory::class.java)
            intent.putExtra(CATEGORY_SEARCH, selectedCategory)
            startActivity(intent)
        }
    }

    private fun setupInitialSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.default_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
            binding.categorySpinner.isEnabled = false  // Disable spinner initially
        }
    }

    private fun setupRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_income -> {
                    updateSpinner(R.array.income_categories)
                    binding.categorySpinner.isEnabled = true
                }
                R.id.radio_expense -> {
                    updateSpinner(R.array.expense_categories)
                    binding.categorySpinner.isEnabled = true
                }
            }
        }
    }

    private fun updateSpinner(arrayId: Int) {
        ArrayAdapter.createFromResource(
            this,
            arrayId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpinnerInteraction() {
        binding.categorySpinner.setOnTouchListener { _, _ ->
            if (!binding.categorySpinner.isEnabled) {
                Toast.makeText(this, "Please select Income or Expense first.", Toast.LENGTH_SHORT).show()
                true  // Consume the touch event to prevent interaction
            } else {
                false  // Allow normal handling of the touch event
            }
        }
    }
}
