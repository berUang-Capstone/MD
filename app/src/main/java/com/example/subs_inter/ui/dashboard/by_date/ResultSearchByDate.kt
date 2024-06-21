package com.example.subs_inter.ui.dashboard.by_date

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subs_inter.R
import com.example.subs_inter.databinding.ActivityResultSearchByDateBinding
import com.example.subs_inter.ui.adapter.dashboard.ByDateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultSearchByDate : AppCompatActivity() {
    companion object {
        const val START_DATE = "startDate"
        const val END_DATE = "endDate"
    }
    private lateinit var binding: ActivityResultSearchByDateBinding
    private lateinit var rvAdapter: ByDateAdapter
    private val viewModel: ResultByDateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDate = intent.getStringExtra(START_DATE) ?: "2024-01-01"
        val endDate = intent.getStringExtra(END_DATE) ?: "2024-01-02"

        viewModel.fetchByDate(startDate, endDate)

        binding = ActivityResultSearchByDateBinding.inflate(layoutInflater)

        rvAdapter = ByDateAdapter()
        binding.recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@ResultSearchByDate)
        }

        viewModel.trxDatas.observe(this) {
            rvAdapter.setData(it.transaction)
        }
        setContentView(binding.root)
    }

}