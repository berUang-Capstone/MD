package com.example.subs_inter.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.ui.adapter.FinanceAdapter
import com.example.subs_inter.databinding.FragmentIncomeBinding
import com.example.subs_inter.ui.expense.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncomeFragment : Fragment() {
    private var _binding: FragmentIncomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IncomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        viewModel.fetchWallet()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.wallet.observe(viewLifecycleOwner) {
            val financeItems = mutableListOf<FinanceAdapter.FinanceItem>()

            financeItems.add(FinanceAdapter.FinanceItem("Salary", it.salary, ContextCompat.getColor(requireContext(), R.color.Salary_color)))
            financeItems.add(FinanceAdapter.FinanceItem("Bonus", it.bonus, ContextCompat.getColor(requireContext(), R.color.Bonus_color)))
            financeItems.add(FinanceAdapter.FinanceItem("Investment", it.investment, ContextCompat.getColor(requireContext(), R.color.income_color)))

            val adapter = FinanceAdapter(financeItems.toList())
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
