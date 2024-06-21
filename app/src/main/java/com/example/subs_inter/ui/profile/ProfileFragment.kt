package com.example.subs_inter.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentHomeBinding
import com.example.subs_inter.databinding.FragmentProfileBinding
import com.example.subs_inter.ui.AboutUsActivity
import com.example.subs_inter.ui.auth.login.LoginActivity
import com.example.subs_inter.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textAboutUs.setOnClickListener {
            aboutus()
        }

        binding.textLogout.setOnClickListener {
            logout()
        }
    }

    private fun aboutus() {
        val intent = Intent(requireContext(), AboutUsActivity::class.java)
        startActivity(intent)
    }
    private fun logout() {
        viewModel.logout(){
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }



}