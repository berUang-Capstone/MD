package com.example.subs_inter.ui.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.subs_inter.ui.add.AddFragment
import com.example.subs_inter.ui.expense.ExpenseFragment
import com.example.subs_inter.ui.income.IncomeFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var mode = MODE_NORMAL

    companion object {
        const val MODE_NORMAL = 0
        const val MODE_ADD = 1
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("SectionsPagerAdapter", "Creating fragment at position $position with mode $mode")
        return when (mode) {
            1 -> AddFragment()
            else -> when (position) {
                0 -> IncomeFragment()
                else -> ExpenseFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        val itemCount = if (mode == MODE_ADD) 1 else 2
        Log.d("SectionsPagerAdapter", "getItemCount: $itemCount for mode $mode")
        return itemCount
    }

    fun switchMode(newMode: Int) {
        mode = newMode
        notifyDataSetChanged()
        Log.d("SectionsPagerAdapter", "Mode switched to $mode, notifyDataSetChanged called")
    }
}
