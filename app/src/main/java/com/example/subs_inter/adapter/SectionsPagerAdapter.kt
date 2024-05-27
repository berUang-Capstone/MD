package com.example.subs_inter.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.subs_inter.ui.ExpenseFragment
import com.example.subs_inter.ui.IncomeFragment



class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment().also { fragment ->
                fragment.arguments = Bundle().apply {
                    putInt(ARG_POSITION, position + 1)  // Menjadi 1
                    putString(ARG_USERNAME, username)
                }
            }
            1 -> ExpenseFragment().also { fragment ->
                fragment.arguments = Bundle().apply {
                    putInt(ARG_POSITION, position + 1)  // Menjadi 2
                    putString(ARG_USERNAME, username)
                }
            }
            else -> throw IllegalStateException("Invalid position $position")
        }
    }

    override fun getItemCount(): Int = 2

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}