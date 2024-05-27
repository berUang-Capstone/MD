package com.example.subs_inter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.subs_inter.databinding.FragmentExpenseBinding
import com.example.subs_inter.databinding.FragmentIncomeBinding

class ExpenseFragment: Fragment()  {

    private lateinit var binding: FragmentExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }
}