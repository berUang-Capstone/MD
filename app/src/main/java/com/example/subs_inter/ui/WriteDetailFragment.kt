package com.example.subs_inter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentWriteDetailBinding
import com.example.subs_inter.database.Note
import com.example.subs_inter.helper.ViewModelFactory
import com.example.subs_inter.viewmodel.WriteDetailViewModel

class WriteDetailFragment : Fragment() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: Note? = null
    private lateinit var writeDetailViewModel: WriteDetailViewModel
    private var _binding: FragmentWriteDetailBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        writeDetailViewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application)).get(NoteAddUpdateViewModel::class.java)
        note = arguments?.getParcelable(EXTRA_NOTE)
        isEdit = note != null
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
            note = Note()
        }

        _binding = FragmentWriteDetailBinding.inflate(inflater, container, false)
        setupViewModel()
        setupInitialSpinner()
        setupRadioGroup()
        setupSpinnerInteraction()
        setupNextButton()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clean up the binding to avoid memory leaks
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory(requireActivity().application)
        writeDetailViewModel = ViewModelProvider(this, factory).get(WriteDetailViewModel::class.java)
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
