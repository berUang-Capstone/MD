package com.example.subs_inter.ui.add_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentWriteDetailMoneyBinding
import com.example.subs_inter.ui.summary.FragmentSummary
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WriteDetailMoneyFragment : Fragment() {

    private val viewModel: WriteDetailViewModel by activityViewModels()
    private var _binding: FragmentWriteDetailMoneyBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val NOTE_CATEGORY = "noteCategory"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWriteDetailMoneyBinding.inflate(inflater, container, false)
        setupViewModel()
        setupSaveButton()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.note.observe(viewLifecycleOwner) { note ->
            note?.let {
                // Only execute this block if the note is not null
                binding.textViewNoteDetails.text = "Type: ${it.item}\nCategory: ${it.category}"
            } ?: run {
                // Handle the case when note is null
                Toast.makeText(context, "Note is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            val amount = binding.etAmount.text.toString().trim()
            val itemName = binding.etItemName.text.toString().trim()
            val itemQuantity = binding.etItemQuantity.text.toString().trim()

            if (amount.isEmpty() || itemName.isEmpty() || itemQuantity.isEmpty()) {
                Toast.makeText(context, "Amount, Item Name, and Item Quantity cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val amountValue = amount.toLong()
                    val itemQuantityValue = itemQuantity.toLong()
                    val totalAmount = amountValue * itemQuantityValue

                    viewModel.note.value?.let { note ->
                        note.amount = totalAmount.toString()
                        // Notes Name
                        note.title = itemName
                        note.quantity = itemQuantity
                        viewModel.setNote(note)
                        viewModel.insert()
                        Toast.makeText(context, "Note saved successfully", Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(context, "Note is null", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(context, "Invalid number format", Toast.LENGTH_SHORT).show()
                }
            }
            val categoryFragment = FragmentSummary().apply {
                arguments = Bundle().apply {
                    viewModel.note.value?.category?.let {
                        putString(NOTE_CATEGORY, it)
                    }
                }
            }
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.nav_host_fragment, categoryFragment, FragmentSummary::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clean up the binding to avoid memory leaks
    }
}
