package com.example.subs_inter.ui.dashboard

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


import com.example.subs_inter.databinding.FragmentDashboardBinding
import com.example.subs_inter.ui.dashboard.all_trx.GetAllTransactionActivity
import com.example.subs_inter.ui.dashboard.by_category.SearchByCategory
import com.example.subs_inter.ui.dashboard.by_date.SearchByDate
import com.example.subs_inter.ui.dashboard.by_type.SearchByType
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root



        binding.SearchByAll.setOnClickListener {
           startActivity(Intent(context, GetAllTransactionActivity::class.java))
        }

        binding.SearchByDate.setOnClickListener {
            startActivity(Intent(context, SearchByDate::class.java))
        }

        binding.SearchByType.setOnClickListener {
           startActivity(Intent(context, SearchByType::class.java))
        }
        binding.SearchByCategory.setOnClickListener {
            startActivity(Intent(context, SearchByCategory::class.java))
        }

        return root
    }

    private fun setupDatePicker(editText: TextInputEditText) {
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Set the date in the format "YYYY-MM-DD"
                editText.setText(String.format(Locale.US, "%d-%02d-%02d", year, month + 1, dayOfMonth))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
