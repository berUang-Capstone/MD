package com.example.subs_inter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.subs_inter.adapter.SectionsPagerAdapter
import com.example.subs_inter.auth.LoginActivity
import com.example.subs_inter.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import androidx.activity.addCallback
import com.example.subs_inter.R

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        setupOnBackPressed()
        setupTabLayout()

        binding.fab.setOnClickListener {
            val newMode = if (sectionsPagerAdapter.mode == SectionsPagerAdapter.MODE_NORMAL) {
                SectionsPagerAdapter.MODE_ADD
            } else {
                SectionsPagerAdapter.MODE_NORMAL
            }
            sectionsPagerAdapter.switchMode(newMode)
            binding.viewPager.adapter = sectionsPagerAdapter // Re-set the adapter
            binding.viewPager.currentItem = 0 // Ensure it navigates to the correct fragment
            setupTabLayout()
            Log.d("HomeFragment", "FAB clicked, mode switched to $newMode, ViewPager adapter reset and position set to 0")
        }

    }

    private fun setupTabLayout() {
        tabLayoutMediator?.detach()

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (sectionsPagerAdapter.mode) {
                SectionsPagerAdapter.MODE_ADD -> getString(R.string.add)
                else -> getString(if (position == 0) R.string.tab_text_1 else R.string.tab_text_2)
            }
        }
        tabLayoutMediator?.attach()
        Log.d("HomeFragment", "TabLayoutMediator set up, mode is ${sectionsPagerAdapter.mode}")
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            binding.viewPager.adapter = sectionsPagerAdapter
            if (sectionsPagerAdapter.mode == SectionsPagerAdapter.MODE_ADD) {
                sectionsPagerAdapter.switchMode(SectionsPagerAdapter.MODE_NORMAL)
                setupTabLayout()
                Log.d("HomeFragment", "Back pressed, mode switched to normal")
            } else {
                isEnabled = false
                activity?.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tabLayoutMediator?.detach()
    }
}
