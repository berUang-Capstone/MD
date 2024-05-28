package com.example.subs_inter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentDashboardBinding
import com.example.subs_inter.databinding.FragmentTakeImageBinding
import com.example.subs_inter.viewmodel.DashboardViewModel


class TakeImage : Fragment() {
    private var _binding: FragmentTakeImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentTakeImageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            binding.textDashboard.text = it
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide BottomNavigationView when the fragment resumes
        (activity as? MainActivity)?.hideBottomNav(true)
    }

    override fun onPause() {
        super.onPause()
        // Show BottomNavigationView when the fragment pauses
        (activity as? MainActivity)?.hideBottomNav(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? MainActivity)?.hideBottomNav(false)
    }
}
