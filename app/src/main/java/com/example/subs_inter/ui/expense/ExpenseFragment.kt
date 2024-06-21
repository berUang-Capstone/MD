package com.example.subs_inter.ui.expense

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
import com.example.subs_inter.databinding.FragmentExpenseBinding
import com.example.subs_inter.databinding.FragmentIncomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseFragment: Fragment() {
    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        viewModel.fetchWallet()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.wallet.observe(viewLifecycleOwner) {
            val financeItems = mutableListOf<FinanceAdapter.FinanceItem>()

            financeItems.add(FinanceAdapter.FinanceItem("Shopping", it.shopping, ContextCompat.getColor(requireContext(), R.color.Shopping_color)))
            financeItems.add(FinanceAdapter.FinanceItem("Food", it.food, ContextCompat.getColor(requireContext(), R.color.Food_color)))
            financeItems.add(FinanceAdapter.FinanceItem("Transportation", it.transportation, ContextCompat.getColor(requireContext(), R.color.Transportation_color)))
            financeItems.add(FinanceAdapter.FinanceItem("Other", it.others, ContextCompat.getColor(requireContext(), R.color.light_grey)))

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
