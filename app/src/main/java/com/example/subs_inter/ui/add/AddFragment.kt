package com.example.subs_inter.ui.add


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentAddBinding

import com.example.subs_inter.ui.MainActivity
import com.example.subs_inter.ui.add_detail.WriteDetailFragment
import com.example.subs_inter.ui.upload.TakeImage

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnWriteManually.setOnClickListener {
            switchWriteDetailFragment()
        }
        binding.btnScanReceipt.setOnClickListener {
            switchToTakeImageFragment()
        }
    }

    private fun switchWriteDetailFragment() {
        val writeDetailFragment = WriteDetailFragment()
        (activity as MainActivity).hideBottomNav(true)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.nav_host_fragment, writeDetailFragment, WriteDetailFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }
    private fun switchToTakeImageFragment() {
        val takeImageFragment = TakeImage()
        (activity as MainActivity).hideBottomNav(true)

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.nav_host_fragment, takeImageFragment, TakeImage::class.java.simpleName)
            addToBackStack(null)
            commit()
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
    }
}
