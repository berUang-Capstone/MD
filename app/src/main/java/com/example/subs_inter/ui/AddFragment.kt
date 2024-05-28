package com.example.subs_inter.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentAddBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Trying to access the binding outside of the view lifecycle.")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup listener for the Scan Receipt button
        binding.btnScanReceipt.setOnClickListener {

            switchToTakeImageFragment()
        }
    }

    private fun switchToTakeImageFragment() {
        val categoryFragment = TakeImage() // Assuming TakeImage is properly set up to be instantiated like this.
        // Use requireActivity() to ensure that the fragment is attached to an activity.
        (activity as MainActivity).hideBottomNav(true)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, categoryFragment, TakeImage::class.java.simpleName)
            addToBackStack(null) // Optional: Comment this out if you do not want this transaction added to the back stack.
            commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding to avoid memory leaks
    }
}
