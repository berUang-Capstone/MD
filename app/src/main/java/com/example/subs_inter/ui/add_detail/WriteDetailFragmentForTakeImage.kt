package com.example.subs_inter.ui.add_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentWriteDetailBinding
import com.example.subs_inter.databinding.FragmentWriteDetailFortakeiamgeBinding
import com.example.subs_inter.ui.summary.FragmentSummary
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WriteDetailFragmentForTakeImage : Fragment() {

    private val viewModel: WriteDetailViewModel by activityViewModels()

    private var _binding: FragmentWriteDetailFortakeiamgeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWriteDetailFortakeiamgeBinding.inflate(inflater, container, false)
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSpinnerInteraction() {
        binding.categorySpinner.setOnTouchListener { _, _ ->
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
                val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
                val selectedRadioButton = view?.findViewById<RadioButton>(selectedRadioButtonId)
                val title = selectedRadioButton?.text.toString().trim()
                val category = binding.categorySpinner.selectedItem?.toString()?.trim() ?: ""

                viewModel.updateTitleCategoryNote(title, category)
                val categoryFragment = FragmentSummary()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment, categoryFragment, FragmentSummary::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clean up the binding to avoid memory leaks
    }
}
