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


class WriteDetailFragment : Fragment() {

    private lateinit var binding: FragmentWriteDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWriteDetailBinding.inflate(inflater, container, false)
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
                Toast.makeText(context, "Please select Income or Expense first.", Toast.LENGTH_SHORT).show()
                true // Consume the touch event to prevent interaction
            } else {
                false // Allow normal handling of the touch event
            }
        }
    }

    private fun setupNextButton() {
        binding.buttonNext.setOnClickListener {
            if (binding.radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(context, "Please select Income or Expense first.", Toast.LENGTH_SHORT).show()
            } else {
                // Add logic to handle the transition to the next screen or action
            }
        }
    }
}
