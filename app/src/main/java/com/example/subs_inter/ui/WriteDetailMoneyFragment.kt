package com.example.subs_inter.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.subs_inter.R
import kotlinx.coroutines.launch

class WriteDetailMoneyFragment : Fragment() {
    private lateinit var textViewDetails: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_write_detail_money, container, false)
        textViewDetails = view.findViewById(R.id.tvDetails)
        loadSavedData()
        return view
    }

    private fun loadSavedData() {
        val db = Room.databaseBuilder(requireContext().applicationContext, WriteDetailFragment.AppDatabase::class.java, "database-name").build()
        lifecycleScope.launch {
            val allSelections = db.userSelectionDao().getAllSelections()
            val stringBuilder = StringBuilder()
            allSelections.forEach {
                stringBuilder.append("ID: ${it.id}, Type: ${it.type}, Category: ${it.category}\n")
            }
            textViewDetails.text = stringBuilder.toString()
        }
    }
}