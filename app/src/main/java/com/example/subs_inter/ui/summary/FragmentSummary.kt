package com.example.subs_inter.ui.summary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.databinding.FragmentSummaryBinding
import com.example.subs_inter.domain.model.NoteModel
import com.example.subs_inter.ui.MainActivity
import com.example.subs_inter.ui.adapter.SummaryAdapter
import com.example.subs_inter.ui.add_detail.WriteDetailFragment
import com.example.subs_inter.ui.add_detail.WriteDetailMoneyFragment
import com.example.subs_inter.ui.add_detail.WriteDetailMoneyFragment.Companion.NOTE_CATEGORY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSummary : Fragment() {

    private val viewModel: SummaryViewModel by viewModels()

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var summaryAdapter: SummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)

        arguments?.getString(NOTE_CATEGORY)?.let {
            Log.d("FS", it)
            viewModel.fetchNotesByCategory(it)
        }

        viewModel.notes.observe(viewLifecycleOwner) {notes ->
            if (notes.isNotEmpty()) {
                val category = notes[0].category
                binding.tvCategory.text = category
                setUpSaveButton(category, notes)
            } else {
                binding.tvCategory.text = "Error Empty"
            }
            summaryAdapter.updateNoteList(notes)
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            navigateToMain()
        }
        setupRecyclerView()
        setupButtons()
        return binding.root
    }

    fun navigateToMain() {
        activity?.let {
            val intent = Intent(it, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            it.startActivity(intent)
        }
    }


    private fun setupRecyclerView() {
        summaryAdapter = SummaryAdapter(listOf()) {
            viewModel.deleteData(it)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = summaryAdapter
        }
    }

    private fun setUpSaveButton(category: String, q: List<NoteModel>) {
        binding.btnSave.setOnClickListener {
            viewModel.uploadData(category = category, notes = q)
        }
    }

    private fun setupButtons() {
        binding.btnAdd.setOnClickListener {
            // Handle Add button click
            Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show()
            val categoryFragment = WriteDetailFragment()
            val fragmentManager = parentFragmentManager
// Check the number of entries in the back stack
            if (fragmentManager.backStackEntryCount > 1) {
                // Get the ID of the fragment before the last fragment
                val id = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).id
                // Pop all inclusive up to the given ID
                fragmentManager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

// Continue with your fragment transaction
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, WriteDetailMoneyFragment())
            transaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
