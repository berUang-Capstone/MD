package com.example.subs_inter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.adapter.FinanceAdapter
import com.example.subs_inter.databinding.FragmentIncomeBinding

class IncomeFragment : Fragment() {
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val financeItems = listOf(
            FinanceAdapter.FinanceItem("Salary", 1000000, ContextCompat.getColor(requireContext(), R.color.income_color)),
            FinanceAdapter.FinanceItem("Bonus", 500000, ContextCompat.getColor(requireContext(), R.color.income_color))
        )

        val adapter = FinanceAdapter(financeItems)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
