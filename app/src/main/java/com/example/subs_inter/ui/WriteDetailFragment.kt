package com.example.subs_inter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentWriteDetailBinding



import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.subs_inter.database.UserSelection
import com.example.subs_inter.database.UserSelectionDao
import kotlinx.coroutines.launch


class WriteDetailFragment : Fragment() {
    @Database(entities = [UserSelection::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userSelectionDao(): UserSelectionDao
    }


    private lateinit var binding: FragmentWriteDetailBinding
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteDetailBinding.inflate(inflater, container, false)
        db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        setupInitialSpinner()
        setupRadioGroup()
        setupSpinnerInteraction()
        setupNextButton()
        return binding.root
    }

    private fun setupInitialSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
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
                    binding.categorySpinner.isEnabled = true  // Enable spinner on selection
                }

                R.id.radio_expense -> {
                    updateSpinner(R.array.expense_categories)
                    binding.categorySpinner.isEnabled = true  // Enable spinner on selection
                }
            }
        }
    }


    private fun updateSpinner(arrayId: Int) {
        ArrayAdapter.createFromResource(
            requireContext(),
            arrayId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
        }
    }

    private fun setupSpinnerInteraction() {
        binding.categorySpinner.setOnTouchListener { v, event ->
            if (!binding.categorySpinner.isEnabled) {
                Toast.makeText(
                    context,
                    "Please select Income or Expense first.",
                    Toast.LENGTH_SHORT
                ).show()
                true // Consume the touch event to prevent interaction
            } else {
                false // Allow normal handling of the touch event
            }
        }
    }

    private fun setupNextButton() {
        binding.buttonNext.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(
                    context,
                    "Please select Income or Expense first.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val selectedType = when (binding.radioGroup.checkedRadioButtonId) {
                    R.id.radio_income -> "Income"
                    R.id.radio_expense -> "Expense"
                    else -> ""
                }
                val selectedCategory = binding.categorySpinner.selectedItem.toString()

                // Use coroutines to perform database operation
                lifecycleScope.launch {
                    db.userSelectionDao().insertUserSelection(
                        UserSelection(
                            type = selectedType,
                            category = selectedCategory
                        )
                    )
                }

                // Continue to next fragment or activity
            }
        }

    }
}
