package com.example.subs_inter.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentHomeBinding
import com.example.subs_inter.ui.MainActivity
import com.example.subs_inter.ui.adapter.SectionsPagerAdapter
import com.example.subs_inter.ui.auth.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private var tabLayoutMediator: TabLayoutMediator? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        viewModel.fetchWallet()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        setupOnBackPressed()
        setupTabLayout()



        viewModel.wallet.observe(viewLifecycleOwner) {
            binding.financeViewImage.amount = it.balance
            val price = it.balance
            // Save the price using SharedPreferences
            val sharedPref = requireActivity().getSharedPreferences("AppPreferences", AppCompatActivity.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putFloat("mybalance", price.toFloat())
                apply()
            }
        }

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
        }
    }



    private fun setupTabLayout() {
        tabLayoutMediator?.detach()

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            viewModel.wallet.observe(viewLifecycleOwner) {
                tab.text = when (sectionsPagerAdapter.mode) {
                    SectionsPagerAdapter.MODE_ADD -> "Add Something"
                    else -> {
                        if (position == 0) {
                            val formattedAmount = "Rp " + NumberFormat.getNumberInstance(Locale("id", "ID")).format(it.income)
                            "Income ${formattedAmount}"
                        } else {
                            val formattedAmount = "Rp " + NumberFormat.getNumberInstance(Locale("id", "ID")).format(it.expense)
                            "Expense ${formattedAmount}"
                        }
                    }
                }
            }

        }
        tabLayoutMediator?.attach()
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            binding.viewPager.adapter = sectionsPagerAdapter
            if (sectionsPagerAdapter.mode == SectionsPagerAdapter.MODE_ADD) {
                sectionsPagerAdapter.switchMode(SectionsPagerAdapter.MODE_NORMAL)
                setupTabLayout()
            } else {
                isEnabled = false
                activity?.onBackPressed()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        // Make sure the bottom navigation bar is shown when this fragment is resumed
        (activity as? MainActivity)?.hideBottomNav(false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tabLayoutMediator?.detach()
    }
}
